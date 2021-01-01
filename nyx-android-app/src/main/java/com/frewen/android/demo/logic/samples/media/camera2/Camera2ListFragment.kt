package com.frewen.android.demo.logic.samples.media.camera2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frewen.android.demo.R
import com.frewen.android.demo.adapter.GenericListAdapter
import com.frewen.freeadapter.recyclerview.BaseRvAdapter as BaseRvAdapter1

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

/**
 * 使用Camera2的列表页面
 */
class Camera2ListFragment : Fragment() {
    
    /**
     * View我们就使用一个简单的RecyclerView
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = RecyclerView(requireContext())
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view as RecyclerView
        // 对象的Apply方法，我们其实就是可以在里面直接调用这个对象的方法，而不需要显示声明
        view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            
            // 获取系统的CameraManager
            val cameraManager =
                    requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            // 列出可用的摄像头列表
            val cameraList = enumerateCameras(cameraManager)
            
            val layoutId = android.R.layout.simple_list_item_1
            adapter = GenericListAdapter(cameraList, itemLayoutId = layoutId) { view, item, _ ->
                view.findViewById<TextView>(android.R.id.text1).text = item.title
                view.setOnClickListener {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                            .navigate(Camera2ListFragmentDirections.actionCameraListToCamera(
                                    item.cameraId, item.format))
                }
            }
            
        }
    }
    
    private fun enumerateCameras(cameraManager: CameraManager): List<FormatItem> {
        // 声明一个可变列表
        val availableCameras: MutableList<FormatItem> = mutableListOf()
        //TODO 学习一下Kotlin中数组的filter方法
        val cameraIds = cameraManager.cameraIdList.filter {
            // 描述CameraDevice属性的对象
            // 可以使用CameraManager通过getCameraCharacteristics（String cameraId）进行查询。
            val characteristics = cameraManager.getCameraCharacteristics(it)
            val capabilities = characteristics.get(
                    CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
            capabilities?.contains(
                    CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE) ?: false
        }
        
        cameraIds.forEach { id ->
            // 描述CameraDevice属性的对象
            // 可以使用CameraManager通过getCameraCharacteristics（String cameraId）进行查询。
            val characteristics = cameraManager.getCameraCharacteristics(id)
            // 相机相对于设备屏幕的方向
            val orientation = lensOrientationString(
                    characteristics.get(CameraCharacteristics.LENS_FACING)!!)
            
            // 查询相机的可用能力和输出格式
            val capabilities = characteristics.get(
                    CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)!!
            
            val outputFormats = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!.outputFormats
            
            // 所有相机都必须支持JPEG输出，因此我们不需要检查特性
            availableCameras.add(FormatItem(
                    "$orientation JPEG ($id)", id, ImageFormat.JPEG))
            
            // 看相机是否支持支持RAW功能
            if (capabilities.contains(
                            CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW) &&
                    outputFormats.contains(ImageFormat.RAW_SENSOR)) {
                availableCameras.add(FormatItem(
                        "$orientation RAW ($id)", id, ImageFormat.RAW_SENSOR))
            }
            
            // 看摄像头是否支持景深
            // Return cameras that support JPEG DEPTH capability
            //            if (capabilities.contains(
            //                            CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT) &&
            //                    outputFormats.contains(ImageFormat.DEPTH_JPEG)) {
            //                availableCameras.add(FormatItem(
            //                        "$orientation DEPTH ($id)", id, ImageFormat.DEPTH_JPEG))
            //            }
        }
        return availableCameras
    }
    
    /**
     * Kotlin中的伴生对象
     */
    companion object {
        /** Helper class used as a data holder for each selectable camera format item */
        private data class FormatItem(val title: String, val cameraId: String, val format: Int)
        
        /**
         * 根据摄像头的Device属性参数来获取摄像头的方向
         */
        private fun lensOrientationString(value: Int) = when (value) {
            CameraCharacteristics.LENS_FACING_BACK -> "后置摄像头"
            CameraCharacteristics.LENS_FACING_FRONT -> "前置摄像头"
            CameraCharacteristics.LENS_FACING_EXTERNAL -> "扩展摄像头"
            else -> "未知"
        }
    }
}
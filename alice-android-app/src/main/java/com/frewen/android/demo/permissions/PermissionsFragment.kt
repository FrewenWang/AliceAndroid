package com.frewen.android.demo.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.frewen.android.demo.R

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

/**
 * This [Fragment] requests permissions and, once granted,
 * it will navigate to the next fragment
 */
class PermissionsFragment : Fragment() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (hasPermissions(requireContext())) {
            // If permissions have already been granted, proceed
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                    PermissionsFragmentDirections.actionPermissionsToCamera2List())
        } else {
            // Request camera-related permissions
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }
    }
    
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Takes the user to the success fragment when permission is granted
                Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                        PermissionsFragmentDirections.actionPermissionsToCamera2List())
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    /**
     * Kotlin中的伴生对象
     */
    companion object {
        /** Convenience method used to check if all permissions required by this app are granted */
        // 让针对权限声明数组的每个元素进行全权限检查
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
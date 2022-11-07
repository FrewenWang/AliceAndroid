package com.frewen.android.demo.error

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.ActivityErrorBinding
import com.frewen.aura.toolkits.utils.ToastUtil
import com.frewen.demo.library.ktx.ext.click
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ktx.ext.showMessageDialog
import com.frewen.aura.framework.mvvm.activity.BaseVMDataBindingActivity
import kotlinx.android.synthetic.main.activity_error.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*

class ErrorActivity : BaseVMDataBindingActivity<ErrorViewModel, ActivityErrorBinding>() {

    companion object {
        const val TAG = "ErrorActivity"
    }

    override fun getContentViewId() = R.layout.activity_error

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使用扩展函数，给toolBar设置Title
        toolbar.init("App异常")

        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        errorRestart.click {
            Log.d(TAG, "onCreate() errorRestart.click  called")
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }

        errorSendError.click {
            /** */
            /* */
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessageDialog(it, "发现有Bug不去打作者脸？", "必须打", {
                    val mClipData = ClipData.newPlainText("errorLog", it)
                    // 将ClipData内容放到系统剪贴板里。
                    getSystemService<ClipboardManager>()?.primaryClip = mClipData
                    ToastUtil.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=61511225"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtil.showShort("请先安装QQ")
                    }
                }, "我不敢")
            }
        }
    }

    /**
     * 设置沉浸式状态栏
     */
    override fun enableTranslucentStatusBar(): Boolean {
        return true
    }
}


package com.frewen.demo.library.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * @filename: BaseAndroidViewModel
 * @introduction: 继承自AndroidViewModel的基础类
 * @author: Frewen.Wong
 * @time: 2020/8/16 09:35
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
open class BaseAndroidViewModel constructor(application: Application) : AndroidViewModel(application) {

}
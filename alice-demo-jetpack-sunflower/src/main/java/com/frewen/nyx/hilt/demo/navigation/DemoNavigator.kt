package com.frewen.nyx.hilt.demo.navigation


/**
 * Available screens.
 */
enum class Screens {
    BUTTONS,
    LOGS
}

interface DemoNavigator {
    fun navigateTo(containerId: Int, screen: Screens)
}
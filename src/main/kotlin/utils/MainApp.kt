package utils

import androidx.compose.ui.awt.ComposeWindow
import navipose.navigation.INavigator


object MainApp {
    lateinit var navigator: INavigator

    fun init() {
        CacheUtils.init()
    }

}
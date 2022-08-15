package utils

import androidx.compose.ui.awt.ComposeWindow
import flowevent.FlowEvent
import kotlinx.coroutines.runBlocking
import navipose.navigation.INavigator
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener


object MainApp {
    lateinit var navigator: INavigator

    fun init() {
        CacheUtils.init()
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
            override fun nativeKeyTyped(p0: NativeKeyEvent?) {

            }

            override fun nativeKeyPressed(p0: NativeKeyEvent?) {
//28
                if (p0?.keyCode == 28) {
                    runBlocking {
                        FlowEvent.post("enter")
                    }
                }
            }

            override fun nativeKeyReleased(p0: NativeKeyEvent?) {

            }
        })
    }

}
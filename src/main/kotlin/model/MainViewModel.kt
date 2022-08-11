package model

import androidx.compose.foundation.layout.PaddingValues
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import utils.ADBUtils

/**
 * @Author : wyl
 * @Date : 2022/8/11
 * Desc :
 */
data class State(val connect: String? = "")
sealed class Event {
    object Idle : Event()
    data class Show(var msg: String) : Event()
    data class Connect(var string: String) : Event()
    object LookPage : Event()
    object LookDevices : Event()
}

object MainViewModel : ReduxViewModel() {

    init {
        workScope.launch {
            pendingActions.consumeAsFlow().collect {
                when (it) {
                    is Event.Idle -> {}
                    is Event.Connect -> connect(it.string)
                    is Event.LookPage -> lookPage()
                    is Event.LookDevices -> lookDevices()
                    else -> {}
                }
            }
        }
    }

    fun sendAction(event: Event) {
        workScope.launch {
            pendingActions.send(event)
        }
    }


    private fun connect(ip: String) {
        ADBUtils.connectDevices(ip) {
            setState(it!!)
        }
    }

    private fun lookPage() {
        ADBUtils.lookShowActivityName {
            setState(it!!)
        }
    }

    private fun lookDevices() {
        ADBUtils.lookDevices {
            setState(it!!)
        }
    }

}
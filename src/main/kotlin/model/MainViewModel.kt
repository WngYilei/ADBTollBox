package model

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import utils.ADBUtils

/**
 * @Author : wyl
 * @Date : 2022/8/11
 * Desc :
 */
sealed class State {
    object Idle : State()
    data class Show(var msg: String) : State()
    data class Result(var msg: String) : State()
}

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
                    is Event.Show -> show(it.msg)
                    is Event.Connect -> connect(it.string)
                    is Event.LookPage -> lookPage()
                    is Event.LookDevices -> lookDevices()
                }
            }
        }
    }


    fun sendAction(event: Event) {
        workScope.launch {
            pendingActions.send(event)
        }
    }


    private fun show(msg: String) {
        setState(State.Show(msg))
    }


    private fun connect(ip: String) {
        ADBUtils.connectDevices(ip) {
            it?.let {
                setState(State.Result(msg = it))
            }
        }
    }

    private fun lookPage() {
        ADBUtils.lookShowActivityName {
            it?.let {
                setState(State.Result(msg = it))
            }
        }
    }

    private fun lookDevices() {
        ADBUtils.lookDevices {
            it?.let {
                setState(State.Result(msg = it))
            }
        }
    }

}
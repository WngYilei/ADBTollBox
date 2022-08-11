package model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @Author : wyl
 * @Date : 2022/8/11
 * Desc :
 */
open class ReduxViewModel {
    protected val _state = MutableStateFlow("")
    val state: StateFlow<String>
        get() = _state

    protected val pendingActions = Channel<Event>(Channel.BUFFERED)

    private val viewModelJob = SupervisorJob()
    protected val workScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    protected fun setState(str: String) {
        workScope.launch {
            _state.emit(str)
        }
    }
}


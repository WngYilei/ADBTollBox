package flowevent

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect

object FlowEvent {
     val event = MutableSharedFlow<Any>()


    suspend fun post(data: Any) {
        event.emit(data)
    }

    suspend fun <T> collect(result: (data: T) -> Unit) {
        event.collect {
            result.invoke(it as T)
        }
    }
}
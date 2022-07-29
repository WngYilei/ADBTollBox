import navipose.models.IScreen

sealed class Screens : IScreen {
    object ApkScreen : Screens()
    object LogScreen : Screens()
    object SettingScreen : Screens()
}

object KVUtils {
    private val map = HashMap<String, Any>()
    fun putData(key: String, value: Any): KVUtils {
        map[key] = value
        return this
    }

    fun <T> getData(key: String): T = map[key] as T

    override fun toString(): String {
        println(map.size)
        map.forEach {
            println(it.key + "-" + it.value)
        }
        return super.toString()
    }
}
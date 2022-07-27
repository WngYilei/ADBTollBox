package utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Executors


object ADBUtils {
    private val threadPool = Executors.newSingleThreadExecutor()

    fun installApk(path: String, status: (status: String?) -> Unit) {
        status.invoke("apk安装路径:$path")
        val adbPath = CacheUtils.readAdbPath()
        if (adbPath.isEmpty()) {
            status.invoke("安装失败，请先配置Android SDK")
            return
        }
        threadPool.execute {
            try {
                val rt = Runtime.getRuntime()
                status.invoke("开始执行安装操作")
                val proc = rt.exec("${adbPath}/platform-tools/adb install -t $path")

                val stdInput = BufferedReader(InputStreamReader(proc.inputStream))

                val stdError = BufferedReader(InputStreamReader(proc.errorStream))

                status.invoke("正在安装...")
                var s: String? = null
                while (stdInput.readLine().also { s = it } != null) {
                    println(s)
                    status.invoke(s)
                }
                while (stdError.readLine().also { s = it } != null) {
                    println(s)
                    status.invoke(s)
                }
            } catch (e: Exception) {
                println(e)
                status.invoke("发生错误${e}")
            }
        }
    }


    fun lookDevices(status: (status: String?) -> Unit) {
        executeAdb("devices", status)
    }

    fun lookShowActivityName(status: (status: String?) -> Unit) {
        executeAdb("shell dumpsys window | grep mCurrentFocus", status)
    }


    private fun executeAdb(cmd: String, status: (status: String?) -> Unit) {
        val adbPath = CacheUtils.readAdbPath()
        threadPool.execute {
            try {
                val rt = Runtime.getRuntime()

                val proc = rt.exec("${adbPath}/platform-tools/adb $cmd")

                val stdInput = BufferedReader(InputStreamReader(proc.inputStream))

                val stdError = BufferedReader(InputStreamReader(proc.errorStream))


                var s: String? = null
                while (stdInput.readLine().also { s = it } != null) {
                    println(s)
                    status.invoke(s)
                }
                while (stdError.readLine().also { s = it } != null) {
                    println(s)
                    status.invoke(s)
                }
            } catch (e: Exception) {
                println(e)
                status.invoke("发生错误${e}")
            }
        }
    }
}
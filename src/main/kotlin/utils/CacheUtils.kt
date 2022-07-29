package utils


import KVUtils
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import javax.swing.filechooser.FileSystemView


object CacheUtils {
    const val ADB = "ADB"
    const val IP = "IP"
    private var map = HashMap<String, Any>()
    lateinit var file: File

    fun init() {
        try {
            val desktopPath = FileSystemView.getFileSystemView().homeDirectory
            val fileDirectory = File("${desktopPath}/AdbToolBox")
            if (!fileDirectory.exists()) fileDirectory.mkdir()
            file = File("${fileDirectory.path}/cache")
            if (!file.exists()) file.createNewFile()

        } catch (e: Exception) {
            println(e)
        }
    }

    fun getConfig(key: String): String {
        val mapper = ObjectMapper()
        return try {
            map = mapper.readValue(file, map.javaClass)
            return map[key] as String
        } catch (e: Exception) {
            println(e.message)
            ""
        }
    }

    fun putConfig(key: String, value: String) {
        val objectMapper = ObjectMapper()
        map[key] = value
        objectMapper.writeValue(file, map)
    }

}
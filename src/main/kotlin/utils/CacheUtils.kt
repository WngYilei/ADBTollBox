package utils


import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import javax.swing.filechooser.FileSystemView

object CacheUtils {
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

    fun readAdbPath(): String {
        val mapper = ObjectMapper()
        return try {
            mapper.readValue(file, String::class.java)
        } catch (e: Exception) {
            ""
        }
    }


    fun writeAbdPath(str: String) {
        val objectMapper = ObjectMapper()
        objectMapper.writeValue(file, str)
    }
}
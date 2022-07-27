import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import utils.CacheUtils
import utils.MainApp
import java.io.File
import javax.swing.filechooser.FileSystemView

fun main() = application {
    MainApp.init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "ADB工具箱",
        resizable = true,
        icon = painterResource("drawable/ic_jetquotes_logo.png")
    ) {
        App(window)
    }
}


@Composable
fun test() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        Button(onClick = {
            try {
                val desktopPath = FileSystemView.getFileSystemView().homeDirectory
                val fileDirectory = File("${desktopPath}/AdbToolBox")
                if (!fileDirectory.exists()) fileDirectory.mkdir()

                val cacheFile = File("${fileDirectory.path}/cache")
                if (!cacheFile.exists()) cacheFile.createNewFile()

            } catch (e: Exception) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("$e")
                }
            }
        }) {
            Text("按钮")
        }
    }
}



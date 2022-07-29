import androidx.compose.foundation.layout.Column
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
//        test()
    }
}


@Composable
fun test() {
    Column {
        Button(onClick = {
            CacheUtils.putConfig("A","123123")
        }) {
            Text("存储数据")
        }

        Button(onClick = {
            val data = CacheUtils.getConfig("A")
            println("获取的数据${data}")
        }) {
            Text("读取数据")
        }
    }
}



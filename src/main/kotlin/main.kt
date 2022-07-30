import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import theme.Color2
import theme.Color3
import theme.FunctionItem
import utils.MainApp

fun main() = application {
    MainApp.init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "ADB工具箱",
        resizable = false,
        icon = painterResource("drawable/ic_jetquotes_logo.png")
    ) {
        App(window)
//        test()
    }
}


@Composable
fun test() {
    Row(modifier = Modifier.padding(10.dp)) {
        Box {
            FunctionItem(backgroundColor = Color2, text = "选择APK", icon = "drawable/icon_select_file.png") {

            }
        }
        Box(modifier = Modifier.padding(start = 20.dp)) {
            FunctionItem(backgroundColor = Color3, text = "查看页面", icon = "drawable/icon_select_file.png") {

            }
        }
    }
}



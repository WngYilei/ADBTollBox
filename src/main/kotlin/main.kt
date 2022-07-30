import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = Modifier.size(50.dp).background(Color.Red, shape = RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                painterResource("drawable/ic_adb_select_un.png"), null,
                modifier = Modifier.fillMaxWidth().height(20.dp)
            )
            Text("ADB", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }

}



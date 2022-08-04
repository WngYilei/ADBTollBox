import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import theme.Color2
import theme.Color3
import theme.FunctionItem
import utils.MainApp
import java.util.*

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
    val pointsOfWeek by mutableStateOf(mutableStateListOf(3f, 2f, 6f, 9.3f, 10f, 15f, 8f))
    ChartView(modifier = Modifier, pointsOfWeek)

    Button(onClick = {
        if (pointsOfWeek.size > 10) pointsOfWeek.removeFirst()
        pointsOfWeek.add(Random().nextFloat())
    }) {
        Text("增加")
    }
}



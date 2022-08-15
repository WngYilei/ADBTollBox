import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import utils.MainApp
import java.util.*

fun main() = application {
    MainApp.init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "iBox",
        resizable = false,
        icon = BitmapPainter(useResource("drawable/ic_jetquotes_logo.png", ::loadImageBitmap)),
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



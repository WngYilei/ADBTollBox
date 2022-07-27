import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import theme.XButton
import ui.ApkScreen
import ui.LogScreen
import ui.SettingScreen


@Composable
@Preview
fun App(window: ComposeWindow) {
    var screen by remember {
        mutableStateOf(Screen.ApkScreen)
    }

    DesktopMaterialTheme {
        Row(modifier = Modifier.background(Color.White)) {
            Column(modifier = Modifier.width(100.dp)) {
                Image(painter = painterResource("drawable/ic_jetquotes_logo.png"), "")
                XButton(onClick = {
                    screen = Screen.ApkScreen
                }, isSelect = true, modifier = Modifier.fillMaxWidth()) {
                    Text("安装apk", style = TextStyle(color = Color.White))
                }

                XButton(
                    onClick = {
                        screen = Screen.LogScreen
                    }, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Text("日志页面", style = TextStyle(color = Color.White))
                }

                XButton(
                    onClick = {
                        screen = Screen.SettingScreen
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                ) {
                    Text("设置", style = TextStyle(color = Color.White))
                }
            }

            Box(
                modifier = Modifier.fillMaxSize().background(Color.Cyan, shape = RoundedCornerShape(15.dp))
                    .padding(20.dp)
            ) {
                when (screen) {
                    Screen.ApkScreen -> {
                        ApkScreen(window)
                    }
                    Screen.LogScreen -> {
                        LogScreen()
                    }
                    Screen.SettingScreen -> {
                        SettingScreen()
                    }
                }
            }

        }
    }
}



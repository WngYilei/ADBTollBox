import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import navipose.components.Navigator
import theme.XButton
import ui.ApkScreen
import ui.LogScreen
import ui.SettingScreen
import utils.MainApp


@Composable
@Preview
fun App(window: ComposeWindow) {
    MaterialTheme {
        Row(modifier = Modifier.background(Color.White)) {
            Column(modifier = Modifier.width(100.dp)) {
                Image(painter = painterResource("drawable/ic_jetquotes_logo.png"), "")
                XButton(onClick = {
                    MainApp.navigator.navigate(Screens.ApkScreen)
                }, isSelect = true, modifier = Modifier.fillMaxWidth()) {
                    Text("ADB", style = TextStyle(color = Color.White))
                }

                XButton(
                    onClick = {
                        MainApp.navigator.navigate(Screens.LogScreen)
                    }, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Text("敬请期待", style = TextStyle(color = Color.White))
                }

                XButton(
                    onClick = {
                        MainApp.navigator.navigate(Screens.SettingScreen)
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

                Navigator(Screens.ApkScreen) {
                    MainApp.navigator = this
                    addScreen(Screens.ApkScreen) { ApkScreen(window) }
                    addScreen(Screens.LogScreen) { LogScreen() }
                    addScreen(Screens.SettingScreen) { SettingScreen() }
                }
            }
        }
    }
}



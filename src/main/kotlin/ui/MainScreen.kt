import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import navipose.components.Navigator
import theme.LeftBar
import theme.XButton
import ui.ApkScreen
import ui.LogScreen
import ui.SettingScreen
import utils.MainApp


@Composable
@Preview
fun App(window: ComposeWindow) {
    MaterialTheme {
        Row(modifier = Modifier.background(LeftBar)) {
            Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource("drawable/ic_jetquotes_logo.png"), "", modifier = Modifier.size(60.dp))
                XButton(
                    onClick = {
                        MainApp.navigator.navigate(Screens.ApkScreen)
                    },
                    isSelect = true,
                    selectIcon = "drawable/ic_adb_select.png",
                    unSelectIcon = "drawable/ic_adb_select_un.png",
                    text = "ADB"
                )

                XButton(
                    onClick = {
                        MainApp.navigator.navigate(Screens.LogScreen)
                    },
                    modifier = Modifier.padding(top = 5.dp),
                    selectIcon = "drawable/ic_except_select.png",
                    unSelectIcon = "drawable/ic_except_select_un.png",
                    text = "敬请期待"
                )
                XButton(
                    onClick = {
                        MainApp.navigator.navigate(Screens.SettingScreen)
                    },
                    modifier = Modifier.padding(top = 5.dp),
                    selectIcon = "drawable/ic_setting_select.png",
                    unSelectIcon = "drawable/ic_setting_select_un.png",
                    text = "设置"
                )
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



package ui

import DropBoxPanel
import EditItem
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import noRippleClickable
import showFileSelector
import theme.FunctionButton
import theme.Purple200
import utils.ADBUtils
import utils.CacheUtils


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ApkScreen(window: ComposeWindow) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var apkPath by remember { mutableStateOf("") }
    val statusList = mutableStateListOf<String>()
    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {

        AnimatedVisibility(
            visible = apkPath.isNotEmpty(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource("drawable/start.png"),
                "",
                contentScale = ContentScale.Inside,
                modifier = Modifier.noRippleClickable {
                    if (apkPath.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("请先选择Apk")
                        }
                        return@noRippleClickable
                    }
                    ADBUtils.installApk(apkPath) {
                        statusList.add(it!!)
                    }
                }
            )
        }

    }) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
            Column(modifier = Modifier.fillMaxSize()) {

                DropBoxPanel(
                    window = window,
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    apkPath = it.first()
                }

                Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {

                    Button(
                        onClick = {
                            val folder = Array(2) { "/User/Desktop" }
                            showFileSelector(folder) {
                                apkPath = it
                            }
                        },

                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = FunctionButton,
                        ),
                    ) {
                        Text("选择Apk")
                    }

                    Button(
                        onClick = {
                            ADBUtils.lookShowActivityName {
                                statusList.add(it!!)
                            }
                        },
                        modifier = Modifier.padding(start = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = FunctionButton,
                        ),
                    ) {
                        Text("查看当前Activity名称")
                    }


                    Button(
                        onClick = {
                            ADBUtils.lookDevices {
                                statusList.add(it!!)
                            }
                        },
                        modifier = Modifier.padding(start = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = FunctionButton,
                        ),
                    ) {
                        Text("查看设备")
                    }


                    Row(modifier = Modifier.padding(start = 10.dp)) {
                        var ip by remember { mutableStateOf("") }
                        Button(onClick = {
                            if (ip.isEmpty()) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("请先输入IP")
                                }
                                return@Button
                            }
                            ADBUtils.connectDevices(ip) {
                                statusList.add(it!!)
                            }
                        }) {
                            Text("连接")
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 5.dp, top = 5.dp)
                                .height(40.dp)
                                .width(300.dp)
                                .background(FunctionButton, shape = RoundedCornerShape(5.dp))
                        ) {
                            BasicTextField(
                                value = ip,
                                onValueChange = {
                                    ip = it
                                },
                                modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp),
                                decorationBox = @Composable { innerTextField ->
                                    if (ip.isEmpty())
                                        Text(text = "请输入IP", color = Color.Gray, style = TextStyle(fontSize = 12.sp))
                                    innerTextField()
                                }

                            )
                        }
                    }




                }
                Text("apk 路径：${apkPath}")
                LazyColumn {
                    itemsIndexed(statusList) { index, item ->
                        Text(item)
                    }
                }
            }
        }
    }


}


@Composable
fun SettingScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.height(100.dp).fillMaxWidth(),
            backgroundColor = Purple200,
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        ) {
            Column {
                Text(
                    "欢迎使用ADB工具箱",
                    style = TextStyle(fontSize = 18.sp, color = Color.White),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    "请先进行相关配置",
                    style = TextStyle(fontSize = 16.sp, color = Color.White),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )

            }
        }

        EditItem("SDK Location:", "请输入Android SDK路径", CacheUtils.getConfig(CacheUtils.ADB)) {
            println(it)
            CacheUtils.putConfig(CacheUtils.ADB, it)
        }
        EditItem("设备IP:", "请输入设备根IP", CacheUtils.getConfig(CacheUtils.IP)) {
            println(it)
            CacheUtils.putConfig(CacheUtils.IP, it)
        }


    }
}


@Composable
fun LogScreen() {

}






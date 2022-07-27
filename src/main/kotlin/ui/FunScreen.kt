package ui

import DropBoxPanel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import theme.ButtonColors
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
                            val folder = Array(2) { "/User" }
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
    var isText by remember { mutableStateOf(true) }
    var etPath by remember { mutableStateOf("") }
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

        Box(modifier = Modifier.fillMaxWidth()) {
            if (isText) {
                Text(
                    "SDK Location:" + CacheUtils.readAdbPath(),
                    modifier = Modifier.padding(top = 26.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                OutlinedTextField(
                    value = etPath,
                    label = { Text(text = "请输入Android SDK路径") },//这里label为文本框未输入时显示的文本
                    onValueChange = {
                        etPath = it
                    }
                )
            }

            Box(modifier = Modifier.fillMaxWidth().padding(top = 15.dp), contentAlignment = Alignment.CenterEnd) {
                Button(
                    onClick = {
                        isText = !isText
                        if (isText && etPath.isNotEmpty()) {
                            CacheUtils.writeAbdPath(etPath)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ButtonColors,
                    ),
                ) {
                    Text(if (isText) "更改" else "确定")
                }
            }

        }


    }
}


@Composable
fun LogScreen() {

}






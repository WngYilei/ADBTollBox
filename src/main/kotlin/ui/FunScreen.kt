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
import flowevent.FlowEvent
import kotlinx.coroutines.launch
import model.Event
import model.MainViewModel
import model.State
import noRippleClickable
import showFileSelector
import theme.*
import utils.ADBUtils
import utils.CacheUtils


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ApkScreen(window: ComposeWindow) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var apkPath by remember { mutableStateOf("") }
    val statusList = mutableStateListOf<String>()

    val state = MainViewModel.state.collectAsState()
    state.value.let {
        when (it) {
            is State.Idle -> {}
            is State.Result -> {

            }
            is State.Show -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it.msg)
                }
            }
        }
    }



    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        AnimatedVisibility(
            visible = apkPath.isNotEmpty(), enter = scaleIn(), exit = scaleOut()
        ) {
            Image(painter = painterResource("drawable/start.png"),
                "",
                contentScale = ContentScale.Inside,
                modifier = Modifier.noRippleClickable {
                    if (apkPath.isEmpty()) {
                        MainViewModel.sendAction(Event.Show("请先选择Apk"))
                        return@noRippleClickable
                    }
                    ADBUtils.installApk(apkPath) {
                        statusList.add(it!!)
                    }
                })
        }

    }) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(modifier = Modifier.fillMaxSize()) {
                DropBoxPanel(window = window,
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                        .background(Color2, shape = RoundedCornerShape(10.dp)),
                    content = {
                        Box {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Image(
                                    painterResource("drawable/icon_upload.png"),
                                    null,
                                    modifier = Modifier.size(60.dp).padding(top = 10.dp)
                                )
                                Text(
                                    if (apkPath.isNotEmpty()) "APK文件路径:$apkPath" else "拖拽文件到此区域",
                                    style = TextStyle(fontSize = 12.sp),
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }
                    }) {
                    apkPath = it.first()
                }

                Row(modifier = Modifier.padding(top = 10.dp).fillMaxWidth()) {
                    Column(
                        modifier = Modifier.width(400.dp)
                            .background(color = Color3, shape = RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row {
                            var ip by remember { mutableStateOf("") }
                            val event = FlowEvent.event.collectAsState("")
                            if ("enter" == event.value && ip.isNotEmpty()) {
                                MainViewModel.sendAction(Event.Connect(string = ip))
                            }
                            Button(onClick = {
                                if (ip.isEmpty()) {
                                    MainViewModel.sendAction(Event.Show("请先输入IP"))
                                    return@Button
                                }
                                MainViewModel.sendAction(Event.Connect(string = ip))
                            }, colors = ButtonDefaults.buttonColors(ButtonColors)) {
                                Text("连接")
                            }
                            Box(
                                modifier = Modifier.padding(start = 5.dp, top = 5.dp).height(40.dp)
                                    .width(300.dp)
                                    .background(FunctionButton, shape = RoundedCornerShape(5.dp))
                            ) {
                                BasicTextField(value = ip,
                                    singleLine = true,
                                    onValueChange = {
                                        ip = it
                                    },

                                    modifier = Modifier.fillMaxSize()
                                        .padding(start = 10.dp, top = 10.dp),
                                    decorationBox = @Composable { innerTextField ->
                                        if (ip.isEmpty()) Text(
                                            text = "请输入IP",
                                            color = Color.Gray,
                                            style = TextStyle(fontSize = 12.sp)
                                        )
                                        innerTextField()
                                    }

                                )
                            }
                        }


                        FunctionRow(modifier = Modifier.padding(top = 10.dp), {
                            FunctionItem(
                                backgroundColor = Color2,
                                text = "选择APK",
                                icon = "drawable/icon_select_file.png"
                            ) {
                                val folder = Array(2) { "/User/Desktop" }
                                showFileSelector(folder) {
                                    apkPath = it
                                }
                            }
                        }, {
                            FunctionItem(
                                backgroundColor = Color3,
                                text = "查看页面",
                                icon = "drawable/icon_look_page.png"
                            ) {
                                ADBUtils.lookShowActivityName {
                                    it?.let {
                                        statusList.add(it)
                                    }
                                }
                            }
                        })


                        FunctionRow(modifier = Modifier.padding(top = 10.dp), {
                            FunctionItem(
                                backgroundColor = Color4,
                                text = "查看设备",
                                icon = "drawable/icon_look_devices.png"
                            ) {
                                ADBUtils.lookDevices {
                                    it?.let {
                                       statusList.add(it)
                                    }
                                }
                            }
                        }, {
                            FunctionItem(
                                backgroundColor = Color5,
                                text = "敬请期待",
                                icon = "drawable/ic_exception.png"
                            ) {
                                MainViewModel.sendAction(Event.Show("更多功能，敬请期待"))
                            }
                        })
                    }


                    LazyColumn(
                        modifier = Modifier.width(300.dp).fillMaxHeight().padding(start = 10.dp)
                            .background(color = Color4, shape = RoundedCornerShape(10.dp))
                            .padding(start = 10.dp, top = 5.dp)
                    ) {
                        itemsIndexed(statusList) { index, item ->
                            Text(item, style = TextStyle(fontSize = 13.sp))
                        }
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






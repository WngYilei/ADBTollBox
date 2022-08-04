import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bean.DropBoundsBean
import theme.ButtonColors
import theme.ButtonColorsNo
import theme.Purple200
import utils.CacheUtils

import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetAdapter
import java.awt.dnd.DropTargetDropEvent
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.UIManager
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DropBoxPanel(
    modifier: Modifier,
    window: ComposeWindow,
    component: JPanel = JPanel(),
    content: @Composable () -> Unit,
    onFileDrop: (List<String>) -> Unit
) {

    val dropBoundsBean = remember {
        mutableStateOf(DropBoundsBean())
    }

    Box(modifier = modifier.onPlaced {
        dropBoundsBean.value = DropBoundsBean(
            x = it.positionInWindow().x,
            y = it.positionInWindow().y,
            width = it.size.width,
            height = it.size.height
        )
    }
    ) {

        content()
        LaunchedEffect(true) {
            component.setBounds(
                dropBoundsBean.value.x.roundToInt(),
                dropBoundsBean.value.y.roundToInt(),
                dropBoundsBean.value.width,
                dropBoundsBean.value.height
            )
            window.contentPane.add(component)

            val target = object : DropTarget(component, object : DropTargetAdapter() {
                override fun drop(event: DropTargetDropEvent) {
                    event.acceptDrop(DnDConstants.ACTION_REFERENCE)
                    val dataFlavors = event.transferable.transferDataFlavors
                    dataFlavors.forEach {
                        if (it == DataFlavor.javaFileListFlavor) {
                            val list = event.transferable.getTransferData(it) as List<*>
                            val pathList = mutableListOf<String>()
                            list.forEach { filePath ->
                                pathList.add(filePath.toString())
                            }
                            onFileDrop(pathList)
                        }
                    }
                    event.dropComplete(true)
                }
            }) {
            }
        }

        SideEffect {
            component.setBounds(
                dropBoundsBean.value.x.roundToInt(),
                dropBoundsBean.value.y.roundToInt(),
                dropBoundsBean.value.width,
                dropBoundsBean.value.height
            )
        }

        DisposableEffect(true) {
            onDispose {
                window.contentPane.remove(component)
            }
        }
    }

    fun show(msg: String) {
        val pathList = mutableListOf<String>()
        pathList.add(msg)
        onFileDrop(pathList)
    }
}


fun showFileSelector(
    suffixList: Array<String>,
    onFileSelected: (String) -> Unit
) {
    JFileChooser().apply {
        //设置页面风格
        try {
            val lookAndFeel = UIManager.getSystemLookAndFeelClassName()
            UIManager.setLookAndFeel(lookAndFeel)
            SwingUtilities.updateComponentTreeUI(this)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        fileSelectionMode = JFileChooser.FILES_ONLY
        isMultiSelectionEnabled = false
//        fileFilter = FileNameExtensionFilter("文件过滤", *suffixList)

        val result = showOpenDialog(ComposeWindow())
        if (result == JFileChooser.APPROVE_OPTION) {
            val dir = this.currentDirectory
            val file = this.selectedFile
            onFileSelected(file.absolutePath)
        }
    }
}


//去掉点击阴影
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun EditItem(editStart: String = "", lable: String = "", value: String = "", click: (str: String) -> Unit) {
    var isText by remember { mutableStateOf(true) }
    var etPath by remember { mutableStateOf("") }
    var text by remember { mutableStateOf(editStart + value) }
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp).background(Purple200, shape = RoundedCornerShape(5.dp))
    ) {
        if (isText) {
            Text(
                text,
                modifier = Modifier.padding(top = 26.dp),
                textAlign = TextAlign.Center
            )
        } else {
            OutlinedTextField(
                value = etPath,
                label = { Text(text = lable) },
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
                        click(etPath)
                    }
                    text = editStart + etPath
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


@Composable
fun ChartView(modifier: Modifier = Modifier, points: List<Float>) {
    //每一行的高度
    val heightForRow = 24

    //总行数
    val countForRow = 5

    //小圆圈半径
    val circleRadius = 2.5

    //画布宽度
    val canvasWidth = 100
    //画布高度
    val canvasHeight = heightForRow * countForRow + circleRadius * 2

    //没8 dp 代表1积分 每一行 3积分
    val perY = 8 //24 * 5 /15

    //七平分宽度
    val averageOfWidth = canvasWidth / 7


    Canvas(modifier = modifier.size(width = canvasWidth.dp, canvasHeight.dp), onDraw = {
        //背景横线
        for (index in 0..countForRow) {
            val startY = (index * heightForRow.toFloat() + circleRadius).dp.toPx()
            val endX = size.width
            val endY = startY
            drawLine(
                Color(0xFFEEEEEE),
                start = Offset(x = 0f, y = startY),
                end = Offset(x = endX, y = endY),
                strokeWidth = 2.5f
            )
        }

        //画小圆圈、折线
        for (index in 0 until points.count()) {

            val centerX = (averageOfWidth * index + averageOfWidth / 2).dp.toPx()
            val centerY =
                (heightForRow * countForRow - points[index] * perY + circleRadius).dp.toPx()
            val circleCenter = Offset(centerX, centerY)
            //点
            drawCircle(
                Color(0xff149ee7), radius = circleRadius.dp.toPx(),
                center = circleCenter,
                style = Stroke(width = 5f)
            )
            //线
            if (index < points.count() - 1) {
                val nextPointOffsetX = (averageOfWidth * (index + 1) + averageOfWidth / 2).dp.toPx()
                val nextPointOffsetY =
                    (heightForRow * countForRow - points[(index + 1)] * perY + circleRadius).dp.toPx()
                val nextPoint = Offset(nextPointOffsetX, nextPointOffsetY)
                drawLine(Color(0xFF149EE7), start = circleCenter, end = nextPoint, strokeWidth = 5f)
            }

        }
    })
}

package theme

import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import noRippleClickable
import utils.ADBUtils


@Composable
fun XButton(
    modifier: Modifier = Modifier,
    isSelect: Boolean = false,
    selectIcon: String,
    unSelectIcon: String,
    text: String,
    onClick: () -> Unit,
) {

    val focusRequester = FocusRequester()
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val color = if (isFocused) SELECT else LeftBar
    val icon = if (isFocused) selectIcon else unSelectIcon

    val size = animateSizeAsState(targetValue = if (isFocused) Size(25f, 25f) else Size(20f, 20f))

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(50.dp).background(color, shape = RoundedCornerShape(5.dp))
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource).noRippleClickable {
                focusRequester.requestFocus()
                onClick()
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(icon), null,
                modifier = Modifier.size(size.value.height.dp, size.value.width.dp).fillMaxWidth()
            )
            Text(
                text,
                modifier = Modifier.fillMaxWidth().padding(top = 3.dp),
                textAlign = TextAlign.Center,
                color = ITEM_TEXT,
                style = TextStyle(fontSize = 10.sp)
            )
        }
    }
}

@Composable
fun FunctionItem(backgroundColor: Color, icon: String, text: String, onClick: () -> Unit) {

    Card(elevation = 10.dp, backgroundColor = Color.Transparent, modifier = Modifier.padding(2.dp)) {
        Box(
            modifier = Modifier.width(170.dp).height(100.dp)
                .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                .noRippleClickable {
                    onClick()
                },
        ) {
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Image(
                    painterResource(icon), null,
                    modifier = Modifier.size(50.dp).padding(top = 5.dp)
                )
                Text(text, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
            }
        }
    }
}


@Composable
fun FunctionRow(modifier: Modifier, contentStart: @Composable () -> Unit, contentEnd: @Composable () -> Unit) {
    Row(modifier = modifier) {
        Box {
            contentStart()
        }
        Box(modifier = Modifier.padding(start = 20.dp)) {
            contentEnd()
        }
    }
}

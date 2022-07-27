package theme

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged


@Composable
fun XButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelect: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val focusRequester = FocusRequester()
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    var color = if (isFocused) ButtonColors else ButtonColorsNo
    Button(
        onClick = {
            focusRequester.requestFocus()
            onClick()
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
        ),
        content = {
            content()
        }
    )


}

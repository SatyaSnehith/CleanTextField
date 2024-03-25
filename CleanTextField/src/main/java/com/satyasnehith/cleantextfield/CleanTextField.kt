package com.satyasnehith.cleantextfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CleanTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "Text",
    valueState: MutableState<CleanTextValue> = remember {
        return@remember mutableStateOf(CleanTextValue())
    },
    textStyle: TextStyle = TextStyle.Default,
) {
    val isFocused = remember {
        mutableStateOf(false)
    }
    val placeholderFontSize = animateFloatAsState(
        targetValue = if (isFocused.value) 12f else 16f,
        animationSpec = tween(),
        label = "placeholderFontSize"
    )
    val placeholderVertical = animateFloatAsState(
        targetValue = if (isFocused.value) 4f else 16f,
        animationSpec = tween(),
        label = "placeholderVertical"
    )
    val placeholderStart = animateFloatAsState(
        targetValue = if (isFocused.value) 0f else 12f,
        animationSpec = tween(),
        label = "placeholderVertical",
    )
    val textFontSize = animateFloatAsState(
        targetValue = if (isFocused.value) 14f else 0f,
        animationSpec = tween(),
        label = "textFontSize"
    )
    val textBottomPadding by animateFloatAsState(
        targetValue = if (isFocused.value) 12f else 0f,
        animationSpec = tween(),
        label = "textBottomPadding",
    )
    val textStartPadding by animateFloatAsState(
        targetValue = if (isFocused.value) 12f else 0f,
        animationSpec = tween(),
        label = "textStartPadding",
    )
    var textFieldValueState = remember { mutableStateOf(TextFieldValue()) }

    Box {
        BasicTextField(
            value = textFieldValueState.value,
            onValueChange = { value ->
                valueState.value = valueState.value.copy(text = value.text)
                textFieldValueState.value = value
            },
            modifier = Modifier
//                .background(MaterialTheme.colorScheme.onBackground.copy(0.3f))
                .onFocusChanged {
                    val isEmpty = textFieldValueState.value.text.isEmpty()
                    isFocused.value = if (it.isFocused) true else !isEmpty
                }
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(5.dp))
                .padding(
                    bottom = textBottomPadding.dp,
                    start = textStartPadding.dp
                )
                .fillMaxWidth()
                .then(modifier),
            textStyle = (
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
//                    fontSize = textFontSize.value.sp
                ).merge(textStyle)
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->

                Column(
//                    modifier = Modifier
//                        .padding(4.dp),
                ) {
                    Text(
                        text = placeholder,
                        modifier = Modifier
//                            .background(MaterialTheme.colorScheme.onBackground.copy(0.5f))
                            .padding(
                                start = placeholderStart.value.dp,
                            )
                            .padding(vertical = placeholderVertical.value.dp),
                        style = TextStyle(
                            fontSize = placeholderFontSize.value.sp,
                            color = MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                                .copy(alpha = 0.7f)
                        ),
                    )
                    AnimatedVisibility(
                        visible = isFocused.value,
                        enter = expandIn(animationSpec = tween()),
                        exit = shrinkOut(animationSpec = tween())
                    ) {
                        innerTextField()
                    }
                }
            }
        )
    }
}

class CleanTextValue(
    var text: String = "",
    var error: String = ""
) {
    fun copy(text: String = this.text, error: String = this.error): CleanTextValue {
        return CleanTextValue(text, error)
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun CleanTextFieldPreview() {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        CleanTextField()
        Spacer(modifier = Modifier.height(20.dp))
        CleanTextField()

    }
}
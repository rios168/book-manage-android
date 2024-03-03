package com.book.management.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.book.management.tools.LogUtil

/**
 * Created by Mark on 2024/3/1 001.
 */
@Composable
fun LimitedNumberTextField(value: TextFieldValue,
                           len: Int = 4,
                           modifier: Modifier = Modifier,
                           label: @Composable (() -> Unit)? = null,
                           onValueChange: (TextFieldValue) -> Unit) {

    val onChange: (TextFieldValue) -> Unit = {
        val limitedChar = "0123456789"
        val builder = StringBuilder()
        for (c in it.text) {
            if (c in limitedChar) {
                builder.append(c)
            }
        }
        val temp = builder.toString()
        val currentSel = if (it.selection.end > temp.length) len else it.selection.end
        val result = if (temp.length <= len) {
            TextFieldValue(temp, TextRange(currentSel))
        } else {
            TextFieldValue(builder.toString().take(len), TextRange(currentSel))
        }
        onValueChange(result)
    }
    TextField(value = value, modifier = modifier, onValueChange = onChange, singleLine = true, label = label)
}
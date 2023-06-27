package com.example.musemelody.view.row

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.view.text.PlainTextView
import com.example.musemelody.view.util.DividerView

@Composable
fun PlainRowView(text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = StyleConstant.PADDING_LARGE.dp)) {
        Row(
            modifier = Modifier.height(StyleConstant.ROW_HEIGHT.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            PlainTextView(text)
        }
        DividerView()
    }
}
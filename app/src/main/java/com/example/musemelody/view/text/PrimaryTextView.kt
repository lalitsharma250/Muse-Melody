package com.example.musemelody.view.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.musemelody.constant.StyleConstant

@Composable
fun PrimaryTextView(text: String) {
    Text(text, color = MaterialTheme.colorScheme.primary, fontSize = StyleConstant.FONT_SMALL.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
}
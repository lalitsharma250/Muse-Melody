package com.example.musemelody.view.cell

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musemelody.constant.StyleConstant

const val first = 1
const val last = 0

@Composable
fun GridCellView(
    index: Int,
    colNumber: Int,
    content: @Composable BoxScope.() -> Unit,
) {
    val position = ((index + 1) % colNumber)
    val start = if (position == first) StyleConstant.PADDING_LARGE.dp else StyleConstant.PADDING_SMALL.dp
    val end = if (position == last) StyleConstant.PADDING_LARGE.dp else StyleConstant.PADDING_SMALL.dp

    Box(modifier = Modifier.padding(start = start, end = end, bottom = StyleConstant.PADDING_LARGE.dp), content = content)
}
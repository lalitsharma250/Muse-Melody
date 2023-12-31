package com.example.musemelody.view.collapsingTopAppBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.example.musemelody.view.topAppBar.PlainTopAppBarView

@Composable
fun ColumnCollapsingTopAppBarView(title: String, content: LazyListScope.() -> Unit) {
    val lazyListState = rememberLazyListState()

    Box(Modifier.zIndex(1F)) {
        PlainTopAppBarView(title, visible = visibleTopAppBar(lazyListState))
    }
    LazyColumn(state = lazyListState, content = content)
}

@Composable
private fun visibleTopAppBar(state: LazyListState): Boolean {
    return state.firstVisibleItemScrollOffset > 1
}
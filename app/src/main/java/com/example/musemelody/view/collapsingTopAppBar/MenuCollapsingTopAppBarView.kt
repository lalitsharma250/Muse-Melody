package com.example.musemelody.view.collapsingTopAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.musemelody.view.topAppBar.MenuTopAppBarView

@Composable
fun MenuCollapsingTopAppBarView(title: String, dropdownMenus: @Composable ColumnScope.(callback: () -> Unit) -> Unit, content: LazyListScope.() -> Unit) {
    val lazyListState = rememberLazyListState()

    Box(Modifier.zIndex(1F)) {
        val expanded = remember { mutableStateOf(false) }
        val callback = { expanded.value = !expanded.value }

        MenuTopAppBarView(title, visible = visibleTopAppBar(lazyListState), callback)
        DropdownMenu(expanded = expanded.value, offset = DpOffset((-1).dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = callback) {
            dropdownMenus(callback)
        }
    }
    LazyColumn(state = lazyListState, content = content)
}

@Composable
private fun visibleTopAppBar(state: LazyListState): Boolean {
    return state.firstVisibleItemScrollOffset > 1
}
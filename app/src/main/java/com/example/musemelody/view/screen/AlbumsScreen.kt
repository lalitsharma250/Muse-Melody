package com.example.musemelody.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musemelody.R

import com.example.musemelody.view.cell.AlbumCellView
import com.example.musemelody.view.cell.GridCellView
import com.example.musemelody.view.collapsingTopAppBar.GridCollapsingTopAppBarView
import dev.tcode.thinmp.view.dropdownMenu.ShortcutDropdownMenuItemView
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.player.MiniPlayerView
import com.example.musemelody.view.util.CustomGridCellsFixed
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.EmptyMiniPlayerView
import com.example.musemelody.view.util.EmptyTopAppBarView
import com.example.musemelody.view.util.gridSpanCount
import com.example.musemelody.view.util.miniPlayerHeight
import com.example.musemelody.viewModel.AlbumsViewModel

@ExperimentalFoundationApi
@Composable
fun AlbumsScreen(viewModel: AlbumsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.current
    val miniPlayerHeight = miniPlayerHeight()
    val spanCount: Int = gridSpanCount()

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (miniPlayer) = createRefs()

        GridCollapsingTopAppBarView(title = stringResource(R.string.albums), columns = CustomGridCellsFixed(spanCount)) {
            item(span = { GridItemSpan(spanCount) }) {
                EmptyTopAppBarView()
            }
            itemsIndexed(uiState.albums) { index, album ->
                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopStart)
                ) {
                    val expanded = remember { mutableStateOf(false) }
                    val close = { expanded.value = false }

                    GridCellView(index, spanCount) {
                        AlbumCellView(album.name, album.artistName, album.getImageUri(), Modifier.pointerInput(album.url) {
                            detectTapGestures(onLongPress = { expanded.value = true }, onTap = { navigator.albumDetail(album.id) })
                        })
                    }
                    DropdownMenu(expanded = expanded.value, offset = DpOffset(0.dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = close) {
                        ShortcutDropdownMenuItemView(album.albumId, close)
                    }
                }
            }
            item(span = { GridItemSpan(spanCount) }) {
                EmptyMiniPlayerView()
            }
        }
        Box(modifier = Modifier.constrainAs(miniPlayer) {
            top.linkTo(parent.bottom, margin = (-miniPlayerHeight).dp)
        }) {
            MiniPlayerView()
        }
    }
}
package com.example.musemelody.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musemelody.R
import com.example.musemelody.view.collapsingTopAppBar.MenuCollapsingTopAppBarView
import dev.tcode.thinmp.view.dropdownMenu.ShortcutDropdownMenuItemView
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.player.MiniPlayerView
import com.example.musemelody.view.row.PlainRowView
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.EmptyMiniPlayerView
import com.example.musemelody.view.util.EmptyTopAppBarView
import com.example.musemelody.view.util.miniPlayerHeight
import com.example.musemelody.viewModel.PlaylistsViewModel

@ExperimentalFoundationApi
@Composable
fun PlaylistsScreen(viewModel: PlaylistsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val miniPlayerHeight = miniPlayerHeight()
    val navigator = LocalNavigator.current

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (miniPlayer) = createRefs()

        MenuCollapsingTopAppBarView(title = stringResource(R.string.playlists), dropdownMenus = {
            DropdownMenuItem(text = { Text(stringResource(R.string.edit)) }, onClick = { })
        }) {
            item {
                EmptyTopAppBarView()
            }
            itemsIndexed(uiState.playlists) { index, playlist ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    val expanded = remember { mutableStateOf(false) }
                    val close = { expanded.value = false }

                    PlainRowView(playlist.primaryText, Modifier.pointerInput(playlist.url) {
                        detectTapGestures(onLongPress = { expanded.value = true }, onTap = { navigator.playlistDetail(playlist.id.id) })
                    })
                    DropdownMenu(expanded = expanded.value, offset = DpOffset((-1).dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = close) {
                        DropdownMenuItem(text = { Text(stringResource(R.string.remove_playlist), color = MaterialTheme.colorScheme.primary) }, onClick = {
                            viewModel.deletePlaylist(playlist.id)
                            viewModel.load(context)
                            close()
                        })
                        ShortcutDropdownMenuItemView(playlist.id, close)
                    }
                }
            }
            item {
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
package com.example.musemelody.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.musemelody.model.media.valueObject.SongId
import com.example.musemelody.view.collapsingTopAppBar.ColumnCollapsingTopAppBarView
import com.example.musemelody.view.dropdownMenu.FavoriteSongDropdownMenuItemView
import com.example.musemelody.view.dropdownMenu.PlaylistDropdownMenuItemView
import com.example.musemelody.view.player.MiniPlayerView
import com.example.musemelody.view.playlist.PlaylistPopupView
import com.example.musemelody.view.row.MediaRowView
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.EmptyMiniPlayerView
import com.example.musemelody.view.util.EmptyTopAppBarView
import com.example.musemelody.view.util.miniPlayerHeight
import com.example.musemelody.viewModel.SongsViewModel

@ExperimentalFoundationApi
@Composable
fun SongsScreen(viewModel: SongsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val visiblePopup = remember { mutableStateOf(false) }
    val miniPlayerHeight = miniPlayerHeight()
    var playlistRegisterSongId = SongId("")

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (miniPlayer) = createRefs()

        ColumnCollapsingTopAppBarView(stringResource(R.string.songs)) {
            item {
                EmptyTopAppBarView()
            }
            itemsIndexed(uiState.songs) { index, song ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    val expanded = remember { mutableStateOf(false) }
                    val close = { expanded.value = false }
                    val closePlaylist = {
                        playlistRegisterSongId = song.songId
                        visiblePopup.value = true
                        close()
                    }

                    MediaRowView(song.name, song.artistName, song.getImageUri(), Modifier.pointerInput(index) {
                        detectTapGestures(onLongPress = { expanded.value = true }, onTap = { viewModel.start(index) })
                    })
                    DropdownMenu(expanded = expanded.value, offset = DpOffset((-1).dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = close) {
                        FavoriteSongDropdownMenuItemView(song.songId, close)
                        PlaylistDropdownMenuItemView(song.songId, closePlaylist)
                    }
                }
            }
            item {
                EmptyMiniPlayerView()
            }
        }
        if (visiblePopup.value) {
            PlaylistPopupView(playlistRegisterSongId, visiblePopup)
        }
        Box(modifier = Modifier.constrainAs(miniPlayer) {
            top.linkTo(parent.bottom, margin = (-miniPlayerHeight).dp)
        }) {
            MiniPlayerView()
        }
    }
}
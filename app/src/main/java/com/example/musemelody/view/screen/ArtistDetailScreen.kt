package com.example.musemelody.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musemelody.R
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.model.media.SongModel
import com.example.musemelody.model.media.valueObject.ArtistId
import com.example.musemelody.model.media.valueObject.SongId
import com.example.musemelody.view.cell.AlbumCellView
import com.example.musemelody.view.cell.GridCellView
import com.example.musemelody.view.collapsingTopAppBar.DetailCollapsingTopAppBarView
import com.example.musemelody.view.dropdownMenu.FavoriteArtistDropdownMenuItemView
import com.example.musemelody.view.dropdownMenu.FavoriteSongDropdownMenuItemView
import com.example.musemelody.view.dropdownMenu.PlaylistDropdownMenuItemView
import dev.tcode.thinmp.view.dropdownMenu.ShortcutDropdownMenuItemView
import dev.tcode.thinmp.view.image.ImageView
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.player.MiniPlayerView
import com.example.musemelody.view.playlist.PlaylistPopupView
import com.example.musemelody.view.row.MediaRowView
import com.example.musemelody.view.title.PrimaryTitleView
import com.example.musemelody.view.title.SecondaryTitleView
import com.example.musemelody.view.title.SectionTitleView
import com.example.musemelody.view.util.CustomGridCellsFixed
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.EmptyMiniPlayerView
import com.example.musemelody.view.util.gridSpanCount
import com.example.musemelody.view.util.miniPlayerHeight
import com.example.musemelody.viewModel.ArtistDetailViewModel

@ExperimentalFoundationApi
@Composable
fun ArtistDetailScreen(id: String, viewModel: ArtistDetailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val visiblePopup = remember { mutableStateOf(false) }
    val spanCount: Int = gridSpanCount()
    val imageSize: Dp = LocalConfiguration.current.screenWidthDp.dp / 3
    val miniPlayerHeight = miniPlayerHeight()
    var playlistRegisterSongId = SongId("")
    val navigator = LocalNavigator.current

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (miniPlayer) = createRefs()

        DetailCollapsingTopAppBarView(title = uiState.primaryText, position = StyleConstant.COLLAPSING_TOP_APP_BAR_TITLE_POSITION, columns = CustomGridCellsFixed(spanCount), dropdownMenus = { callback ->
            FavoriteArtistDropdownMenuItemView(ArtistId(id), callback)
            ShortcutDropdownMenuItemView(ArtistId(id), callback)
        }) {
            item(span = { GridItemSpan(spanCount) }) {
                ConstraintLayout(
                    Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenWidthDp.dp)
                ) {
                    val (primary, secondary, tertiary) = createRefs()

                    ImageView(
                        uri = uiState.imageUri, contentScale = ContentScale.FillWidth, modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp), painter = null
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .constrainAs(primary) { top.linkTo(parent.bottom, margin = (-200).dp) }
                            .background(
                                brush = Brush.verticalGradient(
                                    0.0f to MaterialTheme.colorScheme.background.copy(alpha = 0F),
                                    1.0F to MaterialTheme.colorScheme.background,
                                )
                            ),
                    ) {}
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        ImageView(
                            uri = uiState.imageUri, contentScale = ContentScale.FillWidth, modifier = Modifier
                                .size(imageSize)
                                .clip(CircleShape)
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .constrainAs(secondary) {
                                top.linkTo(parent.bottom, margin = (-StyleConstant.COLLAPSING_TOP_APP_BAR_TITLE_POSITION).dp)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        PrimaryTitleView(uiState.primaryText)
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .constrainAs(tertiary) {
                                top.linkTo(parent.bottom, margin = (-55).dp)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        SecondaryTitleView(uiState.secondaryText)
                    }
                }
            }
            if (uiState.albums.isNotEmpty()) {
                item(span = { GridItemSpan(spanCount) }) {
                    SectionTitleView(stringResource(R.string.albums))
                }
                itemsIndexed(items = uiState.albums) { index, album ->
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
            }
            if (uiState.songs.isNotEmpty()) {
                item(span = { GridItemSpan(spanCount) }) {
                    SectionTitleView(stringResource(R.string.songs))
                }
                itemsIndexed(items = uiState.songs, span = { _: Int, _: SongModel -> GridItemSpan(spanCount) }) { index, song ->
                    Box {
                        val expanded = remember { mutableStateOf(false) }
                        val closeFavorite = { expanded.value = false }
                        val closePlaylist = {
                            playlistRegisterSongId = song.songId
                            visiblePopup.value = true
                            expanded.value = false
                        }

                        MediaRowView(song.name, song.artistName, song.getImageUri(), Modifier.pointerInput(index) {
                            detectTapGestures(onLongPress = { expanded.value = true }, onTap = { viewModel.start(index) })
                        })
                        DropdownMenu(expanded = expanded.value,
                            offset = DpOffset((-1).dp, 0.dp),
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                            onDismissRequest = { expanded.value = false }) {
                            FavoriteSongDropdownMenuItemView(song.songId, closeFavorite)
                            PlaylistDropdownMenuItemView(song.songId, closePlaylist)
                        }
                    }
                }
            }
            item(span = { GridItemSpan(spanCount) }) {
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
package com.example.musemelody.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.musemelody.R
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.view.cell.AlbumCellView
import com.example.musemelody.view.cell.GridCellView
import com.example.musemelody.view.cell.ShortcutCellView
import dev.tcode.thinmp.view.dropdownMenu.ShortcutDropdownMenuItemView
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.player.MiniPlayerView
import com.example.musemelody.view.row.PlainRowView
import com.example.musemelody.view.title.SectionTitleView
import com.example.musemelody.view.util.CustomGridCellsFixed
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.DividerView
import com.example.musemelody.view.util.EmptyMiniPlayerView
import com.example.musemelody.view.util.gridSpanCount
import com.example.musemelody.view.util.miniPlayerHeight
import com.example.musemelody.viewModel.MainViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val miniPlayerHeight = miniPlayerHeight()
    val spanCount: Int = gridSpanCount()
    val navigator = LocalNavigator.current

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (miniPlayer) = createRefs()

        LazyVerticalGrid(columns = CustomGridCellsFixed(spanCount)) {
            item(span = { GridItemSpan(spanCount) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(start = StyleConstant.PADDING_LARGE.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = StyleConstant.PADDING_MEDIUM.dp)
                            .height(StyleConstant.ROW_HEIGHT.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val expanded = remember { mutableStateOf(false) }

                        Text(
                            stringResource(R.string.library),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Left,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = StyleConstant.FONT_HUGE.sp
                        )
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            .size(StyleConstant.BUTTON_SIZE.dp)
                            .clickable { expanded.value = !expanded.value }) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_more_vert_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(StyleConstant.ICON_SIZE.dp)
                            )
                            DropdownMenu(expanded = expanded.value,
                                offset = DpOffset((-1).dp, 0.dp),
                                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                                onDismissRequest = { expanded.value = false }) {
                                DropdownMenuItem(text = { Text(stringResource(R.string.edit), color = MaterialTheme.colorScheme.primary) }, onClick = {
                                    navigator.mainEdit()
                                })
                            }
                        }
                    }
                    DividerView()
                }
            }
            items(items = uiState.menu, span = { GridItemSpan(spanCount) }) { item ->
                if (item.visibility) {
                    PlainRowView(stringResource(item.id), modifier = Modifier.clickable {
                        navController.navigate(item.key)
                    })
                }
            }
            if (uiState.shortcutVisibility && uiState.shortcuts.isNotEmpty()) {
                item(span = { GridItemSpan(spanCount) }) {
                    SectionTitleView(stringResource(R.string.shortcut))
                }
                itemsIndexed(items = uiState.shortcuts) { index, shortcut ->
                    Box(
                        modifier = Modifier.wrapContentSize(Alignment.TopStart)
                    ) {
                        val expanded = remember { mutableStateOf(false) }
                        val close = { expanded.value = false }
                        val callback = {
                            viewModel.load(context)
                            close()
                        }

                        GridCellView(index, spanCount) {
                            ShortcutCellView(shortcut.primaryText, shortcut.secondaryText, shortcut.imageUri, shortcut.type, Modifier.pointerInput(shortcut.url) {
                                detectTapGestures(onLongPress = { expanded.value = true }, onTap = { navController.navigate(shortcut.url) })
                            })
                        }
                        DropdownMenu(expanded = expanded.value, offset = DpOffset(0.dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = close) {
                            ShortcutDropdownMenuItemView(shortcut.itemId, callback)
                        }
                    }
                }
            }
            if (uiState.recentlyAlbumsVisibility && uiState.albums.isNotEmpty()) {
                item(span = { GridItemSpan(spanCount) }) {
                    SectionTitleView(stringResource(R.string.recently_added))
                }
                itemsIndexed(items = uiState.albums) { index, album ->
                    Box(
                        modifier = Modifier.wrapContentSize(Alignment.TopStart)
                    ) {
                        val expanded = remember { mutableStateOf(false) }
                        val close = { expanded.value = false }
                        val callback = {
                            viewModel.load(context)
                            close()
                        }

                        GridCellView(index, spanCount) {
                            AlbumCellView(album.name, album.artistName, album.getImageUri(), Modifier.pointerInput(album.url) {
                                detectTapGestures(onLongPress = { expanded.value = true }, onTap = { navigator.albumDetail(album.id) })
                            })
                        }
                        DropdownMenu(expanded = expanded.value, offset = DpOffset(0.dp, 0.dp), modifier = Modifier.background(MaterialTheme.colorScheme.onBackground), onDismissRequest = close) {
                            ShortcutDropdownMenuItemView(album.albumId, callback)
                        }
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
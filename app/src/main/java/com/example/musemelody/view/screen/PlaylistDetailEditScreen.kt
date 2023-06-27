package com.example.musemelody.view.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musemelody.R
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.topAppBar.EditTopAppBarView
import com.example.musemelody.view.util.CustomLifecycleEventObserver
import com.example.musemelody.view.util.EmptyTopAppBarView
import com.example.musemelody.viewModel.PlaylistDetailEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun PlaylistDetailEditScreen(id: String, viewModel: PlaylistDetailEditViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    val navigator = LocalNavigator.current
    var name by remember { mutableStateOf(uiState.primaryText) }

    CustomLifecycleEventObserver(viewModel)

    ConstraintLayout(Modifier.fillMaxSize()) {
        Box(Modifier.zIndex(3F)) {
            EditTopAppBarView(stringResource(R.string.edit), lazyListState.firstVisibleItemScrollOffset) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    .clickable {
                        viewModel.updateName(PlaylistId(id), name)
                        navigator.back()
                    }) {
                    Text(stringResource(R.string.done), color = MaterialTheme.colorScheme.primary)
                }
            }
        }
        LazyColumn(state = lazyListState) {
            item {
                EmptyTopAppBarView()
            }
            item {
                Text(
                    text = stringResource(R.string.playlist_name),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = StyleConstant.PADDING_LARGE.dp)
                )
            }
            item {
                OutlinedTextField(value = name,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = StyleConstant.PADDING_LARGE.dp, end = StyleConstant.PADDING_LARGE.dp),
                    onValueChange = { name = it })
            }
        }
    }
}
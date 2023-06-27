package com.example.musemelody.view.dropdownMenu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musemelody.R
import com.example.musemelody.model.media.valueObject.SongId
import com.example.musemelody.viewModel.FavoriteSongRegisterViewModel

@Composable
fun FavoriteSongDropdownMenuItemView(id: SongId, close: () -> Unit, viewModel: FavoriteSongRegisterViewModel = viewModel()) {
    if (viewModel.exists(id)) {
        DropdownMenuItem(text = { Text(stringResource(R.string.remove_favorite), color = MaterialTheme.colorScheme.primary) }, onClick = {
            viewModel.delete(id)
            close()
        })
    } else {
        DropdownMenuItem(text = { Text(stringResource(R.string.add_favorite), color = MaterialTheme.colorScheme.primary) }, onClick = {
            viewModel.add(id)
            close()
        })
    }
}
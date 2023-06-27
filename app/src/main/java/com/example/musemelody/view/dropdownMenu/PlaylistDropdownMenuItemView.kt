package com.example.musemelody.view.dropdownMenu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.musemelody.R
import com.example.musemelody.model.media.valueObject.SongId

@Composable
fun PlaylistDropdownMenuItemView(id: SongId, close: () -> Unit) {
    DropdownMenuItem(text = { Text(stringResource(R.string.add_playlist), color = MaterialTheme.colorScheme.primary) }, onClick = {
        close()
    })
}
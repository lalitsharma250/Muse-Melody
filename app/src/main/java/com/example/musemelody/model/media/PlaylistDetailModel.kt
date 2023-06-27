package com.example.musemelody.model.media

import android.net.Uri
import com.example.musemelody.model.media.valueObject.PlaylistId

data class PlaylistDetailModel(
    var id: PlaylistId,
    var primaryText: String,
    var secondaryText: String,
    var imageUri: Uri,
    var songs: List<SongModel> = emptyList()
)
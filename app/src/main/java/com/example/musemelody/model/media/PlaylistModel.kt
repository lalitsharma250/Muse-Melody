package com.example.musemelody.model.media

import com.example.musemelody.constant.NavConstant
import com.example.musemelody.model.media.valueObject.PlaylistId

data class PlaylistModel(
    var id: PlaylistId,
    var primaryText: String,
) {
    val url: String
        get() = "${NavConstant.PLAYLIST_DETAIL}/${this.id.id}"
}
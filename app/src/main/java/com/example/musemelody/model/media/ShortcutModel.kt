package com.example.musemelody.model.media

import android.net.Uri
import com.example.musemelody.constant.NavConstant
import com.example.musemelody.model.media.valueObject.ShortcutItemId
import com.example.musemelody.repository.realm.ItemType

data class ShortcutModel(
    var itemId: ShortcutItemId, var primaryText: String, var secondaryText: String, var imageUri: Uri, var type: ItemType
) {
    val url: String
        get() {
            return when (this.type) {
                ItemType.ARTIST -> "${NavConstant.ARTIST_DETAIL}/${this.itemId.toId(this.type)}"
                ItemType.ALBUM -> "${NavConstant.ALBUM_DETAIL}/${this.itemId.toId(this.type)}"
                ItemType.PLAYLIST -> "${NavConstant.PLAYLIST_DETAIL}/${this.itemId.toId(this.type)}"
            }
        }
}
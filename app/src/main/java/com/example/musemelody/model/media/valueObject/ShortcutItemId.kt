package com.example.musemelody.model.media.valueObject

import com.example.musemelody.repository.realm.ItemType

interface ShortcutItemId {
    fun toId(type: ItemType): String {
        return when (type) {
            ItemType.ARTIST -> (this as ArtistId).id
            ItemType.ALBUM -> (this as AlbumId).id
            ItemType.PLAYLIST -> (this as PlaylistId).id
        }
    }
}
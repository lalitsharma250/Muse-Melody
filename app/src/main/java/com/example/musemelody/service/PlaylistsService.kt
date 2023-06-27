package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.PlaylistModel
import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.repository.realm.PlaylistRepository

class PlaylistsService(val context: Context) {
    fun findAll(): List<PlaylistModel> {
        val repository = PlaylistRepository()
        val playlists = repository.findAll()

        return playlists.map { PlaylistModel(PlaylistId(it.id), it.name) }
    }
}
package com.example.musemelody.register

import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.model.media.valueObject.SongId
import com.example.musemelody.repository.realm.PlaylistRepository

interface PlaylistRegister {
    fun createPlaylist(songId: SongId, text: String) {
        val repository = PlaylistRepository()

        repository.create(songId, text)
    }

    fun addPlaylist(playlistId: PlaylistId, songId: SongId) {
        val repository = PlaylistRepository()

        repository.add(playlistId, songId)
    }

    fun deletePlaylist(playlistId: PlaylistId) {
        val repository = PlaylistRepository()

        repository.delete(playlistId)
    }

    fun updateName(playlistId: PlaylistId, name: String) {
        val repository = PlaylistRepository()

        repository.updateName(playlistId, name)
    }
}
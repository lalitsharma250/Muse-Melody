package com.example.musemelody.register

import com.example.musemelody.model.media.valueObject.SongId
import com.example.musemelody.repository.realm.FavoriteSongRepository

interface FavoriteSongRegister {
    fun exists(songId: SongId): Boolean {
        val repository = FavoriteSongRepository()

        return repository.exists(songId)
    }

    fun add(songId: SongId) {
        val repository = FavoriteSongRepository()

        repository.add(songId)
    }

    fun delete(songId: SongId) {
        val repository = FavoriteSongRepository()

        repository.delete(songId)
    }
}
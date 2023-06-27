package com.example.musemelody.register

import com.example.musemelody.model.media.valueObject.ArtistId
import com.example.musemelody.repository.realm.FavoriteArtistRepository

interface FavoriteArtistRegister {
    fun exists(artistId: ArtistId): Boolean {
        val repository = FavoriteArtistRepository()

        return repository.exists(artistId)
    }

    fun add(artistId: ArtistId) {
        val repository = FavoriteArtistRepository()

        repository.add(artistId)
    }

    fun delete(artistId: ArtistId) {
        val repository = FavoriteArtistRepository()

        repository.delete(artistId)
    }
}
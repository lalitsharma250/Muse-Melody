package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.ArtistModel
import com.example.musemelody.model.media.valueObject.ArtistId
import com.example.musemelody.repository.media.ArtistRepository
import com.example.musemelody.repository.realm.FavoriteArtistRepository

class FavoriteArtistsService(val context: Context, private val favoriteArtistRepository: FavoriteArtistRepository = FavoriteArtistRepository()) {
    fun findAll(): List<ArtistModel> {
        val artistIds = favoriteArtistRepository.findAll()
        val artistRepository = ArtistRepository(context)
        val artists = artistRepository.findByIds(artistIds)

        if (!validation(artistIds, artists)) {
            fix(artistIds, artists)

            return findAll()
        }

        return artistIds.mapNotNull { id ->
            artists.find { it.artistId == id }
        }
    }

    private fun validation(artistIds: List<ArtistId>, artists: List<ArtistModel>): Boolean {
        return artistIds.count() == artists.count()
    }

    private fun fix(artistIds: List<ArtistId>, artists: List<ArtistModel>) {
        val deleteIds = artistIds.filter { id ->
            artists.none { it.artistId == id }
        }

        favoriteArtistRepository.update(deleteIds)
    }
}
package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.ArtistModel
import com.example.musemelody.repository.media.ArtistRepository

class ArtistsService(val context: Context) {
    fun findAll(): List<ArtistModel> {
        val repository = ArtistRepository(context)
        val artists = repository.findAll()

        return artists.sortedBy { it.name }
    }
}
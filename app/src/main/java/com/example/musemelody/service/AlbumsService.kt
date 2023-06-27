package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.AlbumModel
import com.example.musemelody.repository.media.AlbumRepository

class AlbumsService(val context: Context) {
    fun findAll(): List<AlbumModel> {
        val repository = AlbumRepository(context)
        val albums = repository.findAll()

        return albums.sortedBy { it.name }
    }
}
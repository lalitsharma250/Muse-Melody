package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.SongModel
import com.example.musemelody.repository.media.SongRepository

class SongsService(val context: Context) {
    fun findAll(): List<SongModel> {
        val repository = SongRepository(context)
        val songs = repository.findAll()

        return songs.sortedBy { it.name }
    }
}
package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.AlbumDetailModel
import com.example.musemelody.repository.media.AlbumRepository
import com.example.musemelody.repository.media.SongRepository

class AlbumDetailService(val context: Context) {
    fun findById(id: String): AlbumDetailModel? {
        val albumRepository = AlbumRepository(context)
        val songRepository = SongRepository(context)
        val album = albumRepository.findById(id)
        val songs = songRepository.findByAlbumId(id)
        val sortedSongs = songs.sortedBy { it.getTrackNumber() }

        return if (album != null) {
            AlbumDetailModel(id, album.name, album.artistName, album.getImageUri(), sortedSongs)
        } else {
            null
        }
    }
}
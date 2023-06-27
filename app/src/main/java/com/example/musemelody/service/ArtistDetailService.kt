package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.model.media.ArtistDetailModel
import com.example.musemelody.repository.media.AlbumRepository
import com.example.musemelody.repository.media.ArtistRepository
import com.example.musemelody.repository.media.SongRepository

class ArtistDetailService(val context: Context) {
    fun findById(id: String): ArtistDetailModel? {
        val artistRepository = ArtistRepository(context)
        val albumRepository = AlbumRepository(context)
        val songRepository = SongRepository(context)
        val artist = artistRepository.findById(id)
        val albums = albumRepository.findByArtistId(id)
        val sortedAlbums = albums.sortedBy { it.name }
        val songs = songRepository.findByArtistId(id)
        val sortedSongs = songs.sortedWith(compareBy({ it.albumName }, { it.getTrackNumber() }))
        val secondaryText = "${sortedAlbums.count()} albums, ${sortedSongs.count()} songs"
        val imageUri = if (sortedAlbums.isNotEmpty()) {
            sortedAlbums.first().getImageUri()
        } else {
            sortedAlbums.first().getImageUri()
        }

        return if (artist != null) {
            ArtistDetailModel(id, artist.name, secondaryText, imageUri, sortedAlbums, sortedSongs)
        } else {
            null
        }
    }
}
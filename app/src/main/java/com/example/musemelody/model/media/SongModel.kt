package com.example.musemelody.model.media

import android.net.Uri
import android.provider.MediaStore
import com.example.musemelody.constant.MediaConstant
import com.example.musemelody.model.media.valueObject.AlbumId
import com.example.musemelody.model.media.valueObject.ArtistId
import com.example.musemelody.model.media.valueObject.SongId

class SongModel(
    val songId: SongId,
    public override var name: String,
    val artistId: ArtistId,
    val artistName: String,
    val albumId: AlbumId,
    val albumName: String,
    val duration: Int,
    val trackNumber: String
): Music() {
    override var id: String = ""
        get() = songId.id

    fun getImageUri(): Uri {
        return Uri.parse("${MediaConstant.ALBUM_ART}/${albumId.id}")
    }

    fun getMediaUri(): Uri {
        return Uri.parse("${MediaStore.Audio.Media.EXTERNAL_CONTENT_URI}/${id}")
    }

    fun getTrackNumber(): Int {
        val regex = Regex("""\d{1,}""")
        val match = regex.find(trackNumber) ?: return 0

        return match.value.toInt()
    }
}
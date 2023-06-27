package com.example.musemelody.model.media

import com.example.musemelody.constant.NavConstant
import com.example.musemelody.model.media.valueObject.ArtistId

class ArtistModel(
    val artistId: ArtistId,
    public override var name: String,
    public var numberOfAlbums: String,
    public var numberOfTracks: String,
): Music() {
    override var id: String = ""
        get() = artistId.id

    val url: String
        get() =  "${NavConstant.ARTIST_DETAIL}/${this.id}"
}
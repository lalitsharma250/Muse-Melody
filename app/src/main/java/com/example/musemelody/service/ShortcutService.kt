package com.example.musemelody.service

import android.content.Context
import android.content.res.Resources
import com.example.musemelody.R
import com.example.musemelody.model.media.ShortcutModel
import com.example.musemelody.model.media.valueObject.AlbumId
import com.example.musemelody.model.media.valueObject.ArtistId
import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.model.realm.ShortcutRealmModel
import com.example.musemelody.repository.media.AlbumRepository
import com.example.musemelody.repository.media.ArtistRepository
import com.example.musemelody.repository.media.SongRepository
import com.example.musemelody.repository.realm.ItemType
import com.example.musemelody.repository.realm.PlaylistRepository
import com.example.musemelody.repository.realm.ShortcutRepository

class ShortcutService(
    val context: Context,
    private val resources: Resources = context.resources,
    private val shortcutRepository: ShortcutRepository = ShortcutRepository(),
    private val artistRepository: ArtistRepository = ArtistRepository(context),
    private val albumRepository: AlbumRepository = AlbumRepository(context),
    private val songRepository: SongRepository = SongRepository(context),
    private val playlistRepository: PlaylistRepository = PlaylistRepository()
) {
    fun findAll(): List<ShortcutModel> {
        val shortcutRealmModels = shortcutRepository.findAll()
        val group = shortcutRealmModels.groupBy { it.type }
        val map = mutableMapOf(ItemType.ARTIST.ordinal to emptyList<ShortcutModel>(), ItemType.ALBUM.ordinal to emptyList(), ItemType.PLAYLIST.ordinal to emptyList())

        if (group.containsKey(ItemType.ARTIST.ordinal)) {
            val artistIds = group[ItemType.ARTIST.ordinal]?.map { ArtistId(it.itemId) }!!
            val artists = artistRepository.findByIds(artistIds)

            map[ItemType.ARTIST.ordinal] = artists.map {
                val albums = albumRepository.findByArtistId(it.id)


                ShortcutModel(it.artistId, it.name, resources.getString(R.string.artist), albums.first().getImageUri(), ItemType.ARTIST)
            }
        }

        if (group.containsKey(ItemType.ALBUM.ordinal)) {
            val albumIds = group[ItemType.ALBUM.ordinal]?.map { AlbumId(it.itemId) }!!
            val albums = albumRepository.findByIds(albumIds)

            map[ItemType.ALBUM.ordinal] = albums.map {
                ShortcutModel(it.albumId, it.name, resources.getString(R.string.album), it.getImageUri(), ItemType.ALBUM)
            }
        }

        if (group.containsKey(ItemType.PLAYLIST.ordinal)) {
            val playlistIds = group[ItemType.PLAYLIST.ordinal]?.map { PlaylistId(it.itemId) }!!
            val playlists = playlistRepository.findByIds(playlistIds)

            map[ItemType.PLAYLIST.ordinal] = playlists.map {
                val song = songRepository.findById(it.songs.first().songId)

                ShortcutModel(PlaylistId(it.id), it.name, resources.getString(R.string.playlist), song!!.getImageUri(), ItemType.PLAYLIST)
            }
        }

        val shortcutModels = shortcutRealmModels.mapNotNull { shortcut ->
            map[shortcut.type]?.firstOrNull { item -> item.itemId.toId(item.type) == shortcut.itemId }
        }

        if (!validation(shortcutRealmModels, shortcutModels)) {
            fix(shortcutRealmModels, shortcutModels)

            return findAll()
        }

        return shortcutModels
    }

    private fun validation(shortcutRealmModels: List<ShortcutRealmModel>, shortcutModels: List<ShortcutModel>): Boolean {
        return shortcutRealmModels.count() == shortcutModels.count()
    }

    private fun fix(shortcutRealmModels: List<ShortcutRealmModel>, shortcutModels: List<ShortcutModel>) {
        val deleteShortcutIds = shortcutRealmModels.filter { shortcutRealmModel ->
            shortcutModels.none { it.itemId.toId(it.type) == shortcutRealmModel.itemId }
        }.map { it.id }

        shortcutRepository.update(deleteShortcutIds)
    }
}
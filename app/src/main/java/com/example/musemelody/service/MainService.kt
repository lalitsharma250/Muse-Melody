package com.example.musemelody.service

import android.content.Context
import com.example.musemelody.config.ConfigStore
import com.example.musemelody.constant.MainMenuEnum
import com.example.musemelody.constant.MainMenuItem
import com.example.musemelody.model.media.AlbumModel
import com.example.musemelody.model.media.ShortcutModel
import com.example.musemelody.repository.media.AlbumRepository

class MainService(val context: Context) {
    private val albumCount = 20

    fun getMenu(): List<MainMenuItem> {
        return MainMenuEnum.getList(context)
    }

    fun getRecentlyAlbumsVisibility(): Boolean {
        val config = ConfigStore(context)

        return config.getRecentlyAlbumsVisibility()
    }

    fun getRecentlyAlbums(): List<AlbumModel> {
        val repository = AlbumRepository(context)

        return repository.findRecentlyAdded(albumCount)
    }

    fun getShortcutVisibility(): Boolean {
        val config = ConfigStore(context)

        return config.getShortcutVisibility()
    }

    fun getShortcuts(): List<ShortcutModel> {
        val service = ShortcutService(context)

        return service.findAll()
    }
}
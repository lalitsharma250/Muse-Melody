package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.musemelody.constant.MainMenuItem
import com.example.musemelody.model.media.AlbumModel
import com.example.musemelody.model.media.ShortcutModel
import com.example.musemelody.service.MainService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    var menu: List<MainMenuItem> = emptyList(),
    var shortcutVisibility: Boolean = true,
    var shortcuts: List<ShortcutModel> = emptyList(),
    var recentlyAlbumsVisibility: Boolean = true,
    var albums: List<AlbumModel> = emptyList()
)

class MainViewModel(application: Application) : AndroidViewModel(application),
    CustomLifecycleEventObserverListener {
    private var initialized: Boolean = false
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        load(application)
    }

    override fun onResume(context: Context) {
        if (initialized) {
            load(context)
        } else {
            initialized = true
        }
    }

    fun load(context: Context) {
        val service = MainService(context)
        val menu = service.getMenu()
        val shortcutVisibility = service.getShortcutVisibility()
        val shortcuts = if (shortcutVisibility) service.getShortcuts() else emptyList()
        val recentlyAlbumsVisibility = service.getRecentlyAlbumsVisibility()
        val albums = if (recentlyAlbumsVisibility) service.getRecentlyAlbums() else emptyList()

        _uiState.update { currentState ->
            currentState.copy(
                menu = menu, shortcutVisibility = shortcutVisibility, shortcuts = shortcuts, recentlyAlbumsVisibility = recentlyAlbumsVisibility, albums = albums,
            )
        }
    }
}
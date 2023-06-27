package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.musemelody.model.media.ArtistModel
import com.example.musemelody.player.MusicPlayer
import com.example.musemelody.service.FavoriteArtistsService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FavoriteArtistsUiState(
    var artists: List<ArtistModel> = emptyList()
)

class FavoriteArtistsViewModel(application: Application) : AndroidViewModel(application),
    CustomLifecycleEventObserverListener {
    private var initialized: Boolean = false
    private var musicPlayer: MusicPlayer
    private val _uiState = MutableStateFlow(FavoriteArtistsUiState())
    val uiState: StateFlow<FavoriteArtistsUiState> = _uiState.asStateFlow()

    init {
        musicPlayer = MusicPlayer(application)
        load(application)
    }

    fun load(context: Context) {
        val repository = FavoriteArtistsService(context)
        val artists = repository.findAll()

        _uiState.update { currentState ->
            currentState.copy(
                artists = artists
            )
        }
    }

    override fun onResume(context: Context) {
        if (initialized) {
            load(context)
        } else {
            initialized = true
        }
    }
}
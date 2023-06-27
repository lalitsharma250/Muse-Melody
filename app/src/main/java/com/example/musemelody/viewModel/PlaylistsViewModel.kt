package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.musemelody.model.media.PlaylistModel
import com.example.musemelody.register.PlaylistRegister
import com.example.musemelody.service.PlaylistsService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlaylistsUiState(
    var playlists: List<PlaylistModel> = emptyList()
)

class PlaylistsViewModel(application: Application) : AndroidViewModel(application),
    CustomLifecycleEventObserverListener,
    PlaylistRegister {
    private var initialized: Boolean = false
    private val _uiState = MutableStateFlow(PlaylistsUiState())
    val uiState: StateFlow<PlaylistsUiState> = _uiState.asStateFlow()

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
        val repository = PlaylistsService(context)
        val playlists = repository.findAll()

        _uiState.update { currentState ->
            currentState.copy(
                playlists = playlists
            )
        }
    }
}
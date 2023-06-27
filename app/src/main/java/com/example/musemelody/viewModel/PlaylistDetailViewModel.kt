package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.musemelody.model.media.SongModel
import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.player.MusicPlayer
import com.example.musemelody.service.PlaylistDetailService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class PlaylistDetailUiState(
    var primaryText: String = "", var secondaryText: String = "", var imageUri: Uri = Uri.EMPTY, var songs: List<SongModel> = emptyList()
)

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    application: Application, savedStateHandle: SavedStateHandle
) : AndroidViewModel(application), CustomLifecycleEventObserverListener {
    private var initialized: Boolean = false
    private var musicPlayer: MusicPlayer
    private val _uiState = MutableStateFlow(PlaylistDetailUiState())
    val uiState: StateFlow<PlaylistDetailUiState> = _uiState.asStateFlow()
    val id: PlaylistId

    init {
        musicPlayer = MusicPlayer(application)
        id = PlaylistId(savedStateHandle.get<String>("id").toString())

        load(application)
    }

    fun start(index: Int) {
        musicPlayer.start(_uiState.asStateFlow().value.songs, index)
    }

    override fun onResume(context: Context) {
        if (initialized) {
            load(context)
        } else {
            initialized = true
        }
    }

    private fun load(context: Context) {
        val service = PlaylistDetailService(context)
        val playlist = service.findById(id)

        if (playlist != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    primaryText = playlist.primaryText, secondaryText = playlist.secondaryText, imageUri = playlist.imageUri, songs = playlist.songs
                )
            }
        }
    }
}
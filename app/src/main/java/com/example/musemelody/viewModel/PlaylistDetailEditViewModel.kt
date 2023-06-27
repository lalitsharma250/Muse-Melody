package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.musemelody.model.media.valueObject.PlaylistId
import com.example.musemelody.register.PlaylistRegister
import com.example.musemelody.service.PlaylistDetailService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class PlaylistDetailEditUiState(
    var primaryText: String = ""
)

@HiltViewModel
class PlaylistDetailEditViewModel @Inject constructor(
    application: Application, savedStateHandle: SavedStateHandle
) : AndroidViewModel(application), CustomLifecycleEventObserverListener, PlaylistRegister {
    private var initialized: Boolean = false
    private val _uiState = MutableStateFlow(PlaylistDetailEditUiState())
    val uiState: StateFlow<PlaylistDetailEditUiState> = _uiState.asStateFlow()
    val id: PlaylistId

    init {
        id = PlaylistId(savedStateHandle.get<String>("id").toString())

        load(application)
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
                    primaryText = playlist.primaryText
                )
            }
        }
    }
}
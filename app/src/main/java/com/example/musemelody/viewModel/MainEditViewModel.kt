package com.example.musemelody.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.musemelody.config.ConfigStore
import com.example.musemelody.constant.MainMenuItem
import com.example.musemelody.service.MainService
import com.example.musemelody.view.util.CustomLifecycleEventObserverListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainEditUiState(
    var menu: List<MainMenuItem> = emptyList(), var recentlyAlbumsVisibility: Boolean = true, var shortcutVisibility: Boolean = true
)

class MainEditViewModel(application: Application) : AndroidViewModel(application),
    CustomLifecycleEventObserverListener {
    private val _uiState = MutableStateFlow(MainEditUiState())
    val uiState: StateFlow<MainEditUiState> = _uiState.asStateFlow()

    init {
        load(application)
    }

    fun load(context: Context) {
        val service = MainService(context)
        val menu = service.getMenu()
        val shortcutVisibility = service.getShortcutVisibility()
        val recentlyAlbumsVisibility = service.getRecentlyAlbumsVisibility()

        _uiState.update { currentState ->
            currentState.copy(
                menu = menu, recentlyAlbumsVisibility = recentlyAlbumsVisibility, shortcutVisibility = shortcutVisibility
            )
        }
    }

    fun setMainMenuVisibility(target: String) {
        _uiState.update { currentState ->
            val map = currentState.menu.map {
                if (it.key == target) {
                    MainMenuItem(it.id, it.key, !it.visibility)
                } else {
                    it
                }
            }
            currentState.copy(
                menu = map
            )
        }
    }

    fun setRecentlyAlbumsVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                recentlyAlbumsVisibility = !currentState.recentlyAlbumsVisibility
            )
        }
    }

    fun setShortcutVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                shortcutVisibility = !currentState.shortcutVisibility
            )
        }
    }

    fun save(context: Context) {
        val config = ConfigStore(context)

        uiState.value.menu.forEach {
            config.saveMainMenuVisibility(it.key, it.visibility)
        }

        config.saveShortcutVisibility(uiState.value.recentlyAlbumsVisibility)
        config.saveRecentlyAlbumsVisibility(uiState.value.shortcutVisibility)
    }
}
package com.example.musemelody.view.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

interface CustomLifecycleEventObserverListener {
    fun onResume(context: Context) {}
    fun onStop() {}
}

@Composable
fun CustomLifecycleEventObserver(listener: CustomLifecycleEventObserverListener) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val currentOnEvent by rememberUpdatedState(listener)
    val context = LocalContext.current

    DisposableEffect(key1 = lifecycle, key2 = currentOnEvent) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> listener.onResume(context)
                Lifecycle.Event.ON_STOP -> listener.onStop()
                else -> {}
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
}
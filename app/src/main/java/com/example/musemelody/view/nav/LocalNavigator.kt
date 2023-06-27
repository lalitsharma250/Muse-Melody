package com.example.musemelody.view.nav

import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavigator = staticCompositionLocalOf<INavigator> {
    error("Navigator not found")
}
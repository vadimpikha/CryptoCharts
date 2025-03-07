package com.vadimpikha.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.jetbrains.skiko.currentSystemTheme

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    //Workaround for reflecting system theme changing while the app running

    val isDarkThemeState = LocalThemeIsDark.current

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(300)
            isDarkThemeState.value = currentSystemTheme == org.jetbrains.skiko.SystemTheme.DARK
        }
    }
}
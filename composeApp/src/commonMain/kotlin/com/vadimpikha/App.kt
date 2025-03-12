package com.vadimpikha

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vadimpikha.presentation.navigation.Destinations
import com.vadimpikha.presentation.navigation.setupNavigation
import com.vadimpikha.presentation.utils.currentOrThrow
import com.vadimpikha.theme.AppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun App() = AppTheme {
   Surface {
      val navController = rememberNavController()

      SharedTransitionLayout {
         CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            NavHost(
               modifier = Modifier.fillMaxSize(),
               startDestination = Destinations.CoinsList,
               navController = navController
            ) {
               setupNavigation(navController)
            }
         }
      }
   }
}

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

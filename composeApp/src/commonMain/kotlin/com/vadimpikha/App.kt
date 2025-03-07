package com.vadimpikha

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vadimpikha.presentation.navigation.Destinations
import com.vadimpikha.presentation.navigation.setupNavigation
import com.vadimpikha.theme.AppTheme

@Composable
internal fun App() = AppTheme {
   Surface {
      val navController = rememberNavController()

      NavHost(
         modifier = Modifier.fillMaxSize(),
         startDestination = Destinations.CoinsList,
         navController = navController
      ) {
         setupNavigation(navController)
      }
   }
}

package com.vadimpikha.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vadimpikha.presentation.details.CoinDetailsScreen
import com.vadimpikha.presentation.list.CoinsListScreen

fun NavGraphBuilder.setupNavigation(navController: NavController) {
    composable<Destinations.CoinsList> {
        ProvideLocalAnimatedContentScope {
            CoinsListScreen(navController)
        }
    }

    composable<Destinations.CoinDetails> {
        ProvideLocalAnimatedContentScope {
            val coinId = it.toRoute<Destinations.CoinDetails>().id
            CoinDetailsScreen(coinId, navController)
        }
    }
}

val LocalScreenAnimatedContentScope = compositionLocalOf<AnimatedContentScope?> { null }

@Composable
private fun AnimatedContentScope.ProvideLocalAnimatedContentScope(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        value = LocalScreenAnimatedContentScope provides this,
        content = content
    )
}
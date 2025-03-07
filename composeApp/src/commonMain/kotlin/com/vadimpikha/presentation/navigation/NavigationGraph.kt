package com.vadimpikha.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vadimpikha.presentation.details.CoinDetailsScreen
import com.vadimpikha.presentation.list.CoinsListScreen

fun NavGraphBuilder.setupNavigation(navController: NavController) {
    composable<Destinations.CoinsList> {
        CoinsListScreen(navController)
    }

    composable<Destinations.CoinDetails> {
        val coinId = it.toRoute<Destinations.CoinDetails>().id
        CoinDetailsScreen(coinId, navController)
    }
}
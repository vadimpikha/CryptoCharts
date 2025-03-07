package com.vadimpikha.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun CoinDetailsScreen(
    coinId: String,
    navController: NavController
) {

    CoinDetailsScreenContent(
        coinId = coinId,
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
private fun CoinDetailsScreenContent(
    coinId: String,
    onNavigateUp: () -> Unit
) {
    Surface {
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onNavigateUp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.ArrowLeft,
                    contentDescription = "Back"
                )
            }

            Text(text = coinId)
        }
    }
}
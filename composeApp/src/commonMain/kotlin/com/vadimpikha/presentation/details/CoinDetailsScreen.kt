package com.vadimpikha.presentation.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.vadimpikha.LocalSharedTransitionScope
import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.model.getOrNull
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.presentation.components.ScreenLoader
import com.vadimpikha.presentation.components.Toolbar
import com.vadimpikha.presentation.components.ToolbarDefaults
import com.vadimpikha.presentation.navigation.LocalScreenAnimatedContentScope
import com.vadimpikha.presentation.utils.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CoinDetailsScreen(
    coinId: String,
    navController: NavController
) {

    val viewModel = koinViewModel<CoinDetailsViewModel> { parametersOf(coinId) }
    val infoPendingResult by viewModel.info.collectAsStateWithLifecycle()

    CoinDetailsScreenContent(
        coinId = coinId,
        infoPendingResult = infoPendingResult,
        onNavigateUp = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CoinDetailsScreenContent(
    coinId: String,
    infoPendingResult: PendingResult<CoinInfo>,
    onNavigateUp: () -> Unit
) {
    Surface {
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .fillMaxSize(),
        ) {

            Toolbar(
                startSlot = { ToolbarDefaults.NavigateUpButton(onNavigateUp) },
                centerSlot = {
                    val info = infoPendingResult.getOrNull()
                    with(LocalSharedTransitionScope.currentOrThrow()) {
                        ToolbarTitle(
                            coinId = coinId,
                            info = info,
                            animatedVisibilityScope = LocalScreenAnimatedContentScope.currentOrThrow(),
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                endSlot = { Spacer(Modifier.width(56.dp)) },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            when (infoPendingResult) {
                PendingResult.Loading -> ScreenLoader()
                is PendingResult.Data<*> -> Box {}
                is PendingResult.Error -> throw IllegalStateException()
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.ToolbarTitle(
    coinId: String,
    info: CoinInfo?,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val imageKey = "image-$coinId"

        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(info?.iconUrl)
                .placeholderMemoryCacheKey(imageKey)
                .memoryCacheKey(imageKey)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(imageKey),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clip(CircleShape)
                .size(28.dp)
        )

        Text(
            text = info?.symbol?.uppercase().orEmpty(),
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = "symbol-$coinId"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
    }
}
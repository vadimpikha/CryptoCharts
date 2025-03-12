package com.vadimpikha.presentation.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.vadimpikha.LocalSharedTransitionScope
import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.model.getOrNull
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.presentation.components.ScreenLoader
import com.vadimpikha.presentation.navigation.LocalScreenAnimatedContentScope
import com.vadimpikha.presentation.utils.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
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
                coinId = coinId,
                info = infoPendingResult.getOrNull(),
                onNavigateUp = onNavigateUp,
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
private fun Toolbar(
    coinId: String,
    info: CoinInfo?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = onNavigateUp,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.ArrowLeft,
                    contentDescription = "Back"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            with(LocalSharedTransitionScope.currentOrThrow()) {
                AsyncImage(
                    model = info?.iconUrl,
                    contentDescription = null,
                    imageLoader = ImageLoader(LocalPlatformContext.current),
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image-$coinId"),
                            animatedVisibilityScope = LocalScreenAnimatedContentScope.currentOrThrow()
                        )
                        .clip(CircleShape)
                        .size(28.dp)
                )

                Text(
                    text = info?.symbol?.uppercase().orEmpty(),
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "symbol-$coinId"),
                            animatedVisibilityScope = LocalScreenAnimatedContentScope.currentOrThrow()
                        )
                        .widthIn(min = 48.dp)
                )
            }
        }
    }
}
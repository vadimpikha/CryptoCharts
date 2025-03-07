package com.vadimpikha.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.presentation.components.AppScrollbar
import com.vadimpikha.presentation.list.models.UiEffect
import com.vadimpikha.presentation.list.models.UiEvent
import com.vadimpikha.presentation.list.models.UiState
import com.vadimpikha.presentation.navigation.Destinations
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinsListScreen(navController: NavController) {

    val viewModel = koinViewModel<CoinsListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CoinsListScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is UiEffect.OpenCoinDetailsScreen -> {
                    navController.navigate(Destinations.CoinDetails(effect.id))
                }
            }
        }
    }
}

@Composable
private fun CoinsListScreenContent(
    uiState: UiState,
    onEvent: (UiEvent) -> Unit
) {
    Surface {
        when (uiState.coinsInfo) {
            is PendingResult.Data -> CoinsList(uiState.coinsInfo.data, onEvent)
            is PendingResult.Error -> LoadFailed(uiState.coinsInfo.throwable.toString())
            PendingResult.Loading -> ScreenLoader()
        }
    }
}

@Composable
private fun CoinsList(
    coinsInfo: List<CoinInfo>,
    onEvent: (UiEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(coinsInfo, key = CoinInfo::id) { coinInfo ->
                CoinInfoItem(
                    coinInfo = coinInfo,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        AppScrollbar(
            lazyListState = listState,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun LoadFailed(reason: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = reason)
    }
}

@Composable
private fun ScreenLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CoinInfoItem(
    onEvent: (UiEvent) -> Unit,
    coinInfo: CoinInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onEvent(UiEvent.OnCoinClicked(coinInfo.id)) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = coinInfo.iconUrl,
            contentDescription = null,
            imageLoader = ImageLoader(LocalPlatformContext.current),
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
        )

        Text(text = coinInfo.name)
    }
}
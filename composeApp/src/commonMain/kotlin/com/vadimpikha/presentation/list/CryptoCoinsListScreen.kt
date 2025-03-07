package com.vadimpikha.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.vadimpikha.domain.model.LoadableResource
import com.vadimpikha.domain.network.model.CoinInfo
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CryptoCoinsListScreen() {

    val viewModel = koinViewModel<CryptoCoinsListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CryptoCoinsListScreenContent(uiState)
}

@Composable
private fun CryptoCoinsListScreenContent(
    uiState: UiState
) {
    Surface {
        when (uiState.coinsInfo) {
            is LoadableResource.Data -> CryptoCoinsList(uiState.coinsInfo.data)
            is LoadableResource.Error -> ScreenLoadFailed(uiState.coinsInfo.throwable.toString())
            LoadableResource.Loading -> ScreenLoader()
        }
    }
}

@Composable
private fun CryptoCoinsList(coinsInfo: List<CoinInfo>) {
    LazyColumn {
        items(coinsInfo, key = CoinInfo::id) { coinInfo ->
            CoinInfoItem(
                coinInfo = coinInfo,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ScreenLoadFailed(reason: String) {
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
    coinInfo: CoinInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp),
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
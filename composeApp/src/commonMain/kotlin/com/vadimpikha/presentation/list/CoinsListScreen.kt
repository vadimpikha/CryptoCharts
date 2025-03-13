package com.vadimpikha.presentation.list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.vadimpikha.LocalSharedTransitionScope
import com.vadimpikha.domain.model.PendingResult
import com.vadimpikha.domain.network.model.CoinInfo
import com.vadimpikha.domain.network.model.CoinsSorting
import com.vadimpikha.domain.network.model.OrderBy
import com.vadimpikha.presentation.components.AppScrollbar
import com.vadimpikha.presentation.components.ScreenLoader
import com.vadimpikha.presentation.list.models.UiEffect
import com.vadimpikha.presentation.list.models.UiEvent
import com.vadimpikha.presentation.list.models.UiState
import com.vadimpikha.presentation.navigation.Destinations
import com.vadimpikha.presentation.navigation.LocalScreenAnimatedContentScope
import com.vadimpikha.presentation.utils.currentOrThrow
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
            is PendingResult.Data -> CoinsList(uiState.coinsInfo.data, uiState.sorting, onEvent)
            is PendingResult.Error -> LoadFailed(uiState.coinsInfo.throwable.toString())
            PendingResult.Loading -> ScreenLoader()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoinsList(
    coinsInfo: List<CoinInfo>,
    sorting: CoinsSorting,
    onEvent: (UiEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
            modifier = Modifier.fillMaxSize()
        ) {

            stickyHeader {
                TableRowToolbar(
                    currentSorting = sorting,
                    onOptionClick = { onEvent(UiEvent.OnSortingOrderClicked(it)) },
                )
            }

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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CoinInfoItem(
    onEvent: (UiEvent) -> Unit,
    coinInfo: CoinInfo,
    modifier: Modifier = Modifier
) {
    val dividerColor = MaterialTheme.colorScheme.outlineVariant
    Row(
        modifier = modifier
            .clickable { onEvent(UiEvent.OnCoinClicked(coinInfo.id)) }
            .padding(horizontal = 16.dp)
            .drawBehind {
                val thickness = 1.dp.toPx()
                val y = size.height - thickness / 2
                drawLine(
                    color = dividerColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y)
                )
            }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(LocalSharedTransitionScope.currentOrThrow()) {
            val imageKey = "image-${coinInfo.id}"

            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(coinInfo.iconUrl)
                    .placeholderMemoryCacheKey(imageKey)
                    .memoryCacheKey(imageKey)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(imageKey),
                        animatedVisibilityScope = LocalScreenAnimatedContentScope.currentOrThrow()
                    )
                    .clip(CircleShape)
                    .size(48.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = coinInfo.name)
                Text(
                    text = coinInfo.symbol.uppercase(),
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "symbol-${coinInfo.id}"),
                        animatedVisibilityScope = LocalScreenAnimatedContentScope.currentOrThrow()
                    )
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "$${coinInfo.currentPrice}")
                Text(
                    text = "${coinInfo.priceChange}%",
                    color = if (coinInfo.priceChange < 0) Color.Red else Color.Green
                )
            }

        }
    }
}

@Composable
private fun TableRowToolbar(
    currentSorting: CoinsSorting,
    onOptionClick: (OrderBy) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            SortingOption(
                onClick = onOptionClick,
                orderBy = OrderBy.Rank,
                currentSorting = currentSorting,
                modifier = Modifier.width(RankColumnWidth)
            )

            SortingOption(
                onClick = onOptionClick,
                orderBy = OrderBy.MarketCap,
                currentSorting = currentSorting,
                modifier = Modifier.weight(MarketCapColumnWeight),
                alignment = Alignment.Start
            )

            SortingOption(
                onClick = onOptionClick,
                orderBy = OrderBy.CurrentPrice,
                currentSorting = currentSorting,
                modifier = Modifier.weight(CurrentPriceColumnWeight),
                alignment = Alignment.End
            )

            SortingOption(
                onClick = onOptionClick,
                orderBy = OrderBy.PriceChange,
                currentSorting = currentSorting,
                modifier = Modifier.width(PriceChangeColumnWidth)
            )
        }
    }
}

@Composable
private fun SortingOption(
    onClick: (OrderBy) -> Unit,
    orderBy: OrderBy,
    currentSorting: CoinsSorting,
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onClick(orderBy) }
        ),
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = orderBy.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (orderBy == currentSorting.orderBy) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .rotate(if (!currentSorting.ascending) 0f else 180f)
                    .size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private val OrderBy.title: String
    get() = when (this) {
        OrderBy.Rank -> "#"
        OrderBy.CurrentPrice -> "Price"
        OrderBy.PriceChange -> "24h %"
        OrderBy.MarketCap -> "Market Cap"
    }

private val RankColumnWidth = 80.dp
private val PriceChangeColumnWidth = 100.dp
private const val MarketCapColumnWeight = 0.45f
private const val CurrentPriceColumnWeight = 0.55f
package com.vadimpikha.presentation.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
* It will be present on supported platforms only
* */

@Composable
expect fun AppScrollbar(
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
)
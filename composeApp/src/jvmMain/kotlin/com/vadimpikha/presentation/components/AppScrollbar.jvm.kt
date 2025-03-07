package com.vadimpikha.presentation.components

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * It will be present on supported platforms only
 * */
@Composable
actual fun AppScrollbar(
    lazyListState: LazyListState,
    modifier: Modifier
) {
    val adapter = ScrollbarAdapter(lazyListState)
    val contentColor = LocalContentColor.current
    val style = LocalScrollbarStyle.current.copy(
        unhoverColor = contentColor.copy(alpha = 0.23f),
        hoverColor = contentColor.copy(alpha = 0.58f)
    )

    VerticalScrollbar(
        adapter = adapter,
        style = style,
        modifier = modifier
    )
}
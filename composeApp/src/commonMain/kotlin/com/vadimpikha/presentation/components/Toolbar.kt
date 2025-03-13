package com.vadimpikha.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun Toolbar(
    startSlot: (@Composable RowScope.() -> Unit)? = null,
    endSlot: (@Composable RowScope.() -> Unit)? = null,
    centerSlot: (@Composable RowScope.() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(ToolbarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        startSlot?.invoke(this)
        Row(
            modifier = Modifier.weight(1f)
        ) {
            centerSlot?.invoke(this)
        }
        endSlot?.invoke(this)
    }
}

object ToolbarDefaults {

    @Composable
    fun IconButton(
        onClick: () -> Unit,
        painter: Painter,
        modifier: Modifier = Modifier
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier.padding(4.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    @Composable
    fun NavigateUpButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        IconButton(
            onClick = onClick,
            painter = rememberVectorPainter(FeatherIcons.ArrowLeft),
            modifier = modifier
        )
    }

    @Composable
    fun Title(text: String) {
        Text(text = text)
    }
}

private val ToolbarHeight = 56.dp

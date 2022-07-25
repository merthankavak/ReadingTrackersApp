package com.mkavaktech.readingtrackers.core.components.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppBar(
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    onAction: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = onAction
    )
}
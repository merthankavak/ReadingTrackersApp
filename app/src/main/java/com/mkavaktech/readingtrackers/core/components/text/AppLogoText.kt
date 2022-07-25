package com.mkavaktech.readingtrackers.core.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mkavaktech.readingtrackers.R

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name),
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge,
        color = Color.Blue.copy(alpha = 0.5f)
    )
}
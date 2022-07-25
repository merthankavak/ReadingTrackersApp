package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun ReadingStatusCard(
    modifier: Modifier = Modifier,
    label: String = "Reading",
    radius: Int = 24,
    onPress: () -> Unit = {}
) {
    Surface(
        modifier = modifier.clip(
            RoundedCornerShape(
                bottomStartPercent = radius,
                topEndPercent = radius,
            )
        ), color = Color.Blue.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.6f)
                .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.White) }

    }
}
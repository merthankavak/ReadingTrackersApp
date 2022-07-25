package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun WelcomeCard(modifier: Modifier, customUserName: String) {
    Card(
        modifier = modifier
            .padding(5.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append(customUserName)
            }
            append(" Welcome to your reading activity.")
        }, modifier = modifier.padding(15.dp))
    }
}

package com.mkavaktech.readingtrackers.core.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SubmitButton(buttonText: String, loading: Boolean, isValid: Boolean, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
            .fillMaxWidth(),
        enabled = !loading && isValid, shape = CircleShape, onClick = onClick,
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(modifier = Modifier.padding(5.dp), text = buttonText)
    }
}

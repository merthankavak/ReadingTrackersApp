package com.mkavaktech.readingtrackers.core.components.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@ExperimentalMaterial3Api
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    labelText: String = "Password",
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions,
    leadingIcon: ImageVector = Icons.Rounded.Password,
    visualTransformation: VisualTransformation,
) {
    InputField(
        modifier = modifier,
        valueState = passwordState,
        labelText = labelText,
        enabled = enabled,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        onAction = onAction,
        leadingIcon = leadingIcon,
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) })
}

@ExperimentalMaterial3Api
@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(
        onClick = { passwordVisibility.value = !visible },
    ) {
        Icon(imageVector = Icons.Rounded.Visibility, contentDescription = "Visibility Icon")
    }
}
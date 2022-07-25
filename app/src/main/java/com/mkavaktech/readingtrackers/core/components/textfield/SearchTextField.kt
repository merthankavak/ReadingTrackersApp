package com.mkavaktech.readingtrackers.core.components.textfield

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
@ExperimentalComposeUiApi
fun SearchTextField(
    modifier: Modifier = Modifier,
    hint: String = "Search", onSearch: (String) -> Unit = {}
) {
    val searchState = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember(searchState.value) { searchState.value.trim().isNotEmpty() }
    InputField(
        modifier = modifier, valueState = searchState, labelText = hint, enabled = true,
        onAction = KeyboardActions {
            if (!isValid) return@KeyboardActions
            onSearch(searchState.value.trim())
            searchState.value = ""
            keyboardController?.hide()
        },
        leadingIcon = Icons.Rounded.Search,
    )
}


package com.mkavaktech.readingtrackers.view.auth.login

import EmailTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mkavaktech.readingtrackers.core.components.button.SubmitButton
import com.mkavaktech.readingtrackers.core.components.text.AppLogo
import com.mkavaktech.readingtrackers.core.components.textfield.PasswordTextField
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.view.auth.login.viewmodel.LoginViewModel

@ExperimentalComposeUiApi
@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppLogo(modifier = Modifier.padding(top = 50.dp, bottom = 20.dp))
            if (showLoginForm.value) UserForm(
                loading = false,
                isRegister = false
            ) { email, password ->
                viewModel.signInWithEmailAndPassword(email, password) {
                    navController.navigate(NavigationEnums.HomeView.name)
                }
            }
            else {
                UserForm(loading = false, isRegister = true) { email, password ->
                    viewModel.signUpWithEmailAndPassword(email, password) {
                        navController.navigate(NavigationEnums.HomeView.name)
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val infoText =
                if (showLoginForm.value) "Don't have an account? " else "Already have an account? "
            val clickableText = if (showLoginForm.value) "Sign Up" else "Login"

            Text(
                text = infoText, style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
            Text(text = clickableText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    })
        }

    }
}


@ExperimentalComposeUiApi
@Composable
fun UserForm(
    loading: Boolean = false,
    isRegister: Boolean = false,
    onDone: (String, String) -> Unit
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.isNotEmpty()
    }
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    Column(
        modifier = Modifier
            .height(350.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailTextField(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() })
        PasswordTextField(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!isValid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            },
            visualTransformation = visualTransformation
        )
        SubmitButton(
            buttonText = if (isRegister) "Register" else "Login",
            loading = loading,
            isValid = isValid,
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}







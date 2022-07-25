package com.mkavaktech.readingtrackers.view.auth.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mkavaktech.readingtrackers.core.components.text.AppLogo
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import kotlinx.coroutines.delay

@Composable
fun SplashView(navController: NavController) {
    val scale = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        delay(2000L)
        checkAuth(navController)
    }
    Surface(
        modifier = Modifier
            .padding(14.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 3.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) { AppLogo() }
    }
}

fun checkAuth(navController: NavController) {
    if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) navController.navigate(
        NavigationEnums.LoginView.name
    ) else navController.navigate(NavigationEnums.HomeView.name)
}

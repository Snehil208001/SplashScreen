package com.example.splashscreen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.splashscreen.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {

    // 1. Set up Animatable states for drop and rotation
    val offsetY = remember { Animatable(-300f) }
    val rotation = remember { Animatable(0f) }

    // 2. Define the animation sequence
    LaunchedEffect(key1 = true) {
        // --- Phase 1: Drop and First Spin (simultaneously) ---
        coroutineScope {
            // Drop animation
            launch {
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            // First 360-degree rotation
            launch {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
                )
            }
        }

        // --- Phase 2: Final Spin (after landing) ---
        // The rotation value is currently at 360, so we animate to 720 for another full turn
        rotation.animateTo(
            targetValue = 720f,
            animationSpec = tween(durationMillis = 800, easing = LinearEasing)
        )

        // --- Phase 3: Navigate ---
        navController.navigate("onboarding") {
            popUpTo("Splash") { inclusive = true }
        }
    }

    // 3. UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = offsetY.value.dp) // Apply the drop
                    .rotate(rotation.value)       // Apply both rotations
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Inwork: Connect. Collaborate. Succeed.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "Design by INVYU",
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
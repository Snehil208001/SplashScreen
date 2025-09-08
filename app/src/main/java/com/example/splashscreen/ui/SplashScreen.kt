package com.example.splashscreen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
    val offsetY = remember { Animatable(-400f) }   // Y starts high
    val offsetX = remember { Animatable(0f) }      // X for curve
    val rotation = remember { Animatable(0f) }     // Spin

    LaunchedEffect(true) {
        coroutineScope {
            // Drop + overshoot + bounce (No changes here)
            launch {
                offsetY.animateTo(
                    targetValue = 100f,
                    animationSpec = tween(durationMillis = 900, easing = LinearEasing)
                )
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }

            // --- MODIFIED SECTION START ---
            // Curve sideways while dropping, landing in the center
            launch {
                offsetX.animateTo(
                    targetValue = 0f, // Final destination is the center
                    animationSpec = keyframes {
                        durationMillis = 900 // Match the drop duration
                        80f at 450 with LinearOutSlowInEasing // Peak of the arc at the halfway point
                    }
                )
            }
            // --- MODIFIED SECTION END ---

            // Rotation synced (No changes here)
            launch {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(durationMillis = 1200, easing = LinearEasing)
                )
            }
        }

        // Navigate after animation
        navController.navigate("onboarding") {
            popUpTo("Splash") { inclusive = true }
        }
    }

    // --- UI Layout (No changes here) ---
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
                    .offset(x = offsetX.value.dp, y = offsetY.value.dp)
                    .rotate(rotation.value)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Inwork: Connect. Collaborate. Succeed.",
                fontSize = 20.sp,
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
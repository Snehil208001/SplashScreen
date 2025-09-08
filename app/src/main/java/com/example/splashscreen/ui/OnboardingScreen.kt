package com.example.splashscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreen.R // Make sure to import your R file

@Composable
fun OnboardingScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add some padding around the content
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. An Image or Illustration
        Image(
            painter = painterResource(id = R.drawable.unnamed), // Add an illustration to your drawable folder
            contentDescription = "Onboarding Illustration",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 2. The Title Text (Corrected)
        Text(
            text = "Welcome to InWork!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. The Description Text
        Text(
            text = "Let's help you get organized, manage your tasks, and boost your productivity.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 4. A Button to navigate
        Button(
            onClick = {
                // Use the NavController to go to the next screen
                navController.navigate("home") {
                    // Prevent going back to onboarding screen
                    popUpTo("onboarding") { inclusive = true }
                }
            },
            modifier = Modifier.size(width = 250.dp, height = 50.dp)
        ) {
            Text(text = "Get Started", fontSize = 18.sp)
        }
    }
}

// A Preview function to see your design in Android Studio without running the app
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(navController = rememberNavController())
}
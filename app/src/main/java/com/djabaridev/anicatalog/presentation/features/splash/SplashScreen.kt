package com.djabaridev.anicatalog.presentation.features.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.R
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavRoute
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1500L)
        navController.navigate(AniCatalogNavRoute.MAIN.name) {
            popUpTo(AniCatalogNavRoute.SPLASH.name) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.myanimelist_logo),
            contentDescription = "mal logo",
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        SplashScreen(navController = rememberNavController())
    }
}
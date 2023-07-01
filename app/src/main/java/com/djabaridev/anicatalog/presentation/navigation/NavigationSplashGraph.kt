package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.djabaridev.anicatalog.presentation.features.splash.SplashScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.splashGraph(navController: NavController) {
    navigation(
        startDestination = AniCatalogNavOption.SPLASH_SCREEN.name,
        route = AniCatalogNavRoute.SPLASH.name
    ) {
        composable(AniCatalogNavOption.SPLASH_SCREEN.name) {
            SplashScreen(navController = navController)
        }
    }
}
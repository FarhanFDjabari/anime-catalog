package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.djabaridev.anicatalog.presentation.features.detail.AnimeDetailScreen
import com.djabaridev.anicatalog.presentation.features.detail.MangaDetailScreen
import com.djabaridev.anicatalog.presentation.features.favorite.FavoriteScreen
import com.djabaridev.anicatalog.presentation.features.favorite.FavoriteViewModel
import com.djabaridev.anicatalog.presentation.features.home.AnimeSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.home.HomeScreen
import com.djabaridev.anicatalog.presentation.features.home.MangaSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.search.SearchScreen
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
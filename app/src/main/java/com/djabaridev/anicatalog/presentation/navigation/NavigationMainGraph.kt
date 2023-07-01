package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.djabaridev.anicatalog.presentation.features.detail.AnimeDetailScreen
import com.djabaridev.anicatalog.presentation.features.detail.MangaDetailScreen
import com.djabaridev.anicatalog.presentation.features.favorite.FavoriteScreen
import com.djabaridev.anicatalog.presentation.features.all.AnimeSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.home.HomeScreen
import com.djabaridev.anicatalog.presentation.features.all.MangaSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.search.SearchScreen

@ExperimentalMaterial3Api
fun NavGraphBuilder.mainGraph(drawerState: DrawerState, navController: NavController) {
    navigation(
        startDestination = AniCatalogNavOption.HOME_SCREEN.name,
        route = AniCatalogNavRoute.MAIN.name
    ) {
        composable(AniCatalogNavOption.HOME_SCREEN.name) {
            HomeScreen(navController = navController, drawerState = drawerState)
        }
        composable(
            "${AniCatalogNavOption.ANIME_SEE_ALL_SCREEN.name}/{year}/{season}",
            arguments = listOf(
                navArgument("year") {
                    type = NavType.IntType
                },
                navArgument("season") {
                    type = NavType.StringType
                },
            )
        ) {
            AnimeSeeAllScreen(navController = navController)
        }
        composable(
            "${AniCatalogNavOption.MANGA_SEE_ALL_SCREEN.name}/{sortType}",
            arguments = listOf(
                navArgument("sortType") {
                    type = NavType.StringType
                },
            )
        ) {
            MangaSeeAllScreen(navController = navController)
        }
        composable(
            "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/{mangaName}/{mangaId}",
            arguments = listOf(
                navArgument("mangaId") {
                    type = NavType.IntType
                },
                navArgument("mangaName") {
                    type = NavType.StringType
                },
            )
        ) {
             MangaDetailScreen(navController = navController)
        }
        composable(
            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/{animeName}/{animeId}",
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                },
                navArgument("animeName") {
                    type = NavType.StringType
                },
            )
        ) {
            AnimeDetailScreen(navController = navController)
        }
        composable(AniCatalogNavOption.SEARCH_SCREEN.name) {
             SearchScreen(navController = navController)
        }
        composable(AniCatalogNavOption.FAVORITE_SCREEN.name) {
             FavoriteScreen(
                 navController = navController,
                 drawerState = drawerState,
             )
        }
    }
}
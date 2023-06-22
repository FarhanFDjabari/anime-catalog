package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.djabaridev.anicatalog.presentation.features.home.HomeScreen
import com.djabaridev.anicatalog.presentation.features.home.AnimeSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.home.MangaSeeAllScreen
import com.djabaridev.anicatalog.presentation.features.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniCatalogNavigation(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AniCatalogDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = AniCatalogNavOption.HOME_SCREEN,
            ) {
                when (it) {
                    AniCatalogNavOption.HOME_SCREEN -> {
                        navController.navigate(it.name) {
                            popUpTo(AniCatalogNavRoute.MAIN.name)
                        }
                    }
                    AniCatalogNavOption.FAVORITE_SCREEN -> {
//                         go to favorite screen without popping backstack
                        navController.navigate(it.name) {
//                            popUpTo(AniCatalogNavRoute.MAIN.name)
                        }
                    }
                    AniCatalogNavOption.LOGOUT -> {
                        // go to login screen without popping backstack
//                        navController.navigate(AniCatalogRoute.LOGOUT.name) {
//                            popUpTo(AniCatalogNavRoute.MAIN.name)
//                        }
                    }
                }
            }
        },
    ) {
        NavHost(
            navController,
            startDestination = AniCatalogNavRoute.MAIN.name,
        ) {
            mainGraph(
                drawerState = drawerState,
                navController = navController,
            )
        }
    }
}
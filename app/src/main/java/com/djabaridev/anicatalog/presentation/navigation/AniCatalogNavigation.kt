package com.djabaridev.anicatalog.presentation.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniCatalogNavigation(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            AniCatalogDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
            ) {
                when (it) {
                    AniCatalogNavOption.HOME_SCREEN -> {
                        navController.navigate(AniCatalogNavOption.HOME_SCREEN.name) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    AniCatalogNavOption.FAVORITE_SCREEN -> {
                        navController.navigate(AniCatalogNavOption.FAVORITE_SCREEN.name) {
//                            popUpTo(AniCatalogNavRoute.MAIN.name) {
//                                saveState = true
//                                inclusive = true
//                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    AniCatalogNavOption.LOGOUT -> {
                        // go to login screen
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
            startDestination = AniCatalogNavRoute.SPLASH.name,
        ) {
            splashGraph(navController = navController)
            mainGraph(
                drawerState = drawerState,
                navController = navController,
            )
        }
    }
}
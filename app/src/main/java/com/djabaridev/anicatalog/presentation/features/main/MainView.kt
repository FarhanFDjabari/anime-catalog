package com.djabaridev.anicatalog.presentation.features.main

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    AniCatalogNavigation(
        navController = navController,
        drawerState = drawerState
    )
}
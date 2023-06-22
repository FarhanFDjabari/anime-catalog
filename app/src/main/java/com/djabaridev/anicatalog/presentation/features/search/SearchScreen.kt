package com.djabaridev.anicatalog.presentation.features.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
fun PreviewSearchScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        SearchScreen(navController = rememberNavController())
    }
}
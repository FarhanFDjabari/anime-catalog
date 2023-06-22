package com.djabaridev.anicatalog.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.R
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeRankingListHeader
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeRankingListItem
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeSeasonalListHeader
import com.djabaridev.anicatalog.presentation.features.home.components.MangaListItem
import com.djabaridev.anicatalog.presentation.features.home.components.MangaRankingListHeader
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, drawerState: DrawerState, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.myanimelist_logo),
                        contentDescription = "MyAnimeList Logo",
                        alignment = Alignment.CenterStart,
                        modifier = Modifier.size(150.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Drawer Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                         navController.navigate(AniCatalogNavOption.SEARCH_SCREEN.name)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // TODO: Anime Ranking Horizontal List
            item {
                AnimeRankingListHeader(modifier = modifier)
            }
            item {
                AnimeRankingListItem(
                    modifier = modifier,
                    onItemClick = {
                        navController.navigate(
                            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/Anime Title/0"
                        )
                    }
                )
            }
            // TODO: Anime Seasonal Vertical List
            item {
                AnimeSeasonalListHeader(modifier = modifier)
            }
            items(5) {
                AnimeListItem(
                    onItemClick = {
                        navController.navigate(
                            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/Anime Title/0"
                        )
                    }
                )
            }
            // TODO: Manga Ranking Vertical List
            item {
                MangaRankingListHeader()
            }
            items(5) {
                MangaListItem(
                    onItemClick = {
                        navController.navigate(
                            "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/Manga Title/0"
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHomeScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        HomeScreen(
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        )
    }
}
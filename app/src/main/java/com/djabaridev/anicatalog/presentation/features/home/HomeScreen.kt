package com.djabaridev.anicatalog.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.R
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeRankingEnum
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaRankingEnum
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeRankingListHeader
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeRankingListItem
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeSeasonalListHeader
import com.djabaridev.anicatalog.presentation.features.home.components.MangaListItem
import com.djabaridev.anicatalog.presentation.features.home.components.MangaRankingListHeader
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import com.djabaridev.anicatalog.utils.ListState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    HomeScreenHeader(
        navController = navController,
        drawerState = drawerState,
        modifier = modifier
    ) {
        HomeScreenContent(
            navController = navController,
            modifier = modifier,
            padding = it,
            animeRankingList = viewModel.animeRankingList,
            animeSeasonalList = viewModel.seasonalAnimeList,
            mangaRankingList = viewModel.mangaRankList,
            animeRankListState = viewModel.animeRankListState.value,
            animeSeasonalListState = viewModel.seasonalAnimeListState.value,
            mangaRankListState = viewModel.mangaRankListState.value,
            onAnimeRankFilterSelected = { sortBy ->
                viewModel.getAnimeRanking(sortBy)
            },
            onMangaRankFilterSelected = {sortBy ->
                viewModel.getMangaRanking(sortBy)
            },
            onAnimeSeasonChanged = { season, year ->
                viewModel.getAnimeSeasonal(season, year)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenHeader(
    navController: NavController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit = {}
) {
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
        content(it)
    }
}

@Composable
fun HomeScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    animeRankingList: List<AniMangaListItemEntity> = emptyList(),
    animeSeasonalList: List<AniMangaListItemEntity> = emptyList(),
    mangaRankingList: List<AniMangaListItemEntity> = emptyList(),
    animeRankListState: ListState = ListState.LOADING,
    animeSeasonalListState: ListState = ListState.LOADING,
    mangaRankListState: ListState = ListState.LOADING,
    onAnimeRankFilterSelected: (AnimeRankingEnum) -> Unit = {},
    onMangaRankFilterSelected: (MangaRankingEnum) -> Unit = {},
    onAnimeSeasonChanged: (String, Int) -> Unit = { _, _ -> },
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            AnimeRankingListHeader(
                modifier = modifier,
                onFilterChange = onAnimeRankFilterSelected
            )
        }
        item {
            AnimeRankingListItem(
                modifier = modifier,
                data = animeRankingList,
                isLoading = animeRankListState == ListState.LOADING,
                onItemClick = {
                    navController.navigate(
                        "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/${it.title}/${it.id}"
                    )
                }
            )
        }
        item {
            AnimeSeasonalListHeader(
                modifier = modifier,
                onFilterChange = onAnimeSeasonChanged
            )
        }
        if (animeSeasonalListState == ListState.IDLE) {
            items(animeSeasonalList.size) {
                AnimeListItem(
                    data = animeSeasonalList[it],
                    isLoading = false,
                    onItemClick = {item ->
                        navController.navigate(
                            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/${item.title}/${item.id}"
                        )
                    }
                )
            }
        } else {
            items(5) {
                AnimeListItem(isLoading = true)
            }
        }
        item {
            MangaRankingListHeader(
                onFilterChange = onMangaRankFilterSelected
            )
        }
        if (mangaRankListState == ListState.IDLE) {
            items(mangaRankingList.size) {
                MangaListItem(
                    data = mangaRankingList[it],
                    isLoading = false,
                    onItemClick = { item ->
                        navController.navigate(
                            "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/${item.title}/${item.id}"
                        )
                    }
                )
            }
        } else {
            items(5) {
                MangaListItem(isLoading = true)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHomeScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        HomeScreenHeader(
            navController = rememberNavController(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            modifier = Modifier
        ) {
            HomeScreenContent(
                navController = rememberNavController(),
                modifier = Modifier,
                padding = it,
            )
        }
    }
}
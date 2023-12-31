package com.djabaridev.anicatalog.presentation.features.favorite

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.favorite.anime.AnimeFavoriteTab
import com.djabaridev.anicatalog.presentation.features.favorite.manga.MangaFavoriteTab
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    drawerState: DrawerState,
    viewModel : FavoriteViewModel = hiltViewModel()
) {

    val favoriteAnime by viewModel.favoriteAnimeList.observeAsState()
    val favoriteManga by viewModel.favoriteMangaList.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                title = {
                    Text(
                        text = "Favorite",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        }
    ) {
        FavoriteScreenTab(
            navController = navController,
            onTabClick = { index -> viewModel.updateTabIndex(index) },
            tabIndex = viewModel.tabIndex.observeAsState(),
            tabs = viewModel.tabs,
            draggableState = viewModel.dragState.value!!,
            onDragStop = { viewModel.updateTabIndexOnSwipe() },
            animes = favoriteAnime,
            mangas = favoriteManga,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun FavoriteScreenTab(
    navController: NavController,
    tabIndex: State<Int?>,
    tabs: List<String>,
    onTabClick: (Int) -> Unit,
    draggableState: DraggableState,
    onDragStop: () -> Unit,
    animes: List<AniMangaListItemEntity>?,
    mangas: List<AniMangaListItemEntity>?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth(),
    ) {
        TabRow(selectedTabIndex = tabIndex.value?: 0) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex.value == index,
                    onClick = { onTabClick(index) },
                    text = {Text(text = title)},
                )
            }
        }
        when (tabIndex.value) {
            0 -> AnimeFavoriteTab(
                navController = navController,
                animes = animes,
                draggableState = draggableState,
                onDragStop = {onDragStop()}
            )
            1 -> MangaFavoriteTab(
                navController = navController,
                mangas = mangas,
                draggableState = draggableState,
                onDragStop = {onDragStop()}
            )
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        FavoriteScreenTab(
            navController = rememberNavController(),
            tabIndex = remember { mutableIntStateOf(0) },
            tabs = listOf("Anime", "Manga"),
            onTabClick = {},
            draggableState = DraggableState { },
            animes = emptyList(),
            mangas = emptyList(),
            onDragStop = {},
        )
    }
}
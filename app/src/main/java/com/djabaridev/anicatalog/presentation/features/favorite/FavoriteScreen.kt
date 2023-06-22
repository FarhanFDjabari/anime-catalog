package com.djabaridev.anicatalog.presentation.features.favorite

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.favorite.anime.AnimeFavoriteTab
import com.djabaridev.anicatalog.presentation.features.favorite.manga.MangaFavoriteTab
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    drawerState: DrawerState,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel : FavoriteViewModel = hiltViewModel()

    val favoriteAnime = viewModel.favoriteAnimeList.observeAsState()
    val favoriteManga = viewModel.favoriteMangaList.observeAsState()

    Log.d("Favorite ViewModel", "${favoriteAnime.value} ")
    Log.d("Favorite ViewModel", "${favoriteManga.value} ")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            animes = favoriteAnime.value,
            mangas = favoriteManga.value,
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
package com.djabaridev.anicatalog.presentation.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableContent
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var isAnimeLoading by remember { mutableStateOf(false) }
    var isMangaLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.animeListEventFlow.collect{ event ->
            when (event) {
                is SearchViewModel.UIEvent.Loading -> {
                    isAnimeLoading = true
                }
                is SearchViewModel.UIEvent.DataLoaded -> {
                    isAnimeLoading = false
                }
                is SearchViewModel.UIEvent.ShowSnackbar -> {
                    isAnimeLoading = false
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.mangaListEventFlow.collect{ event ->
            when (event) {
                is SearchViewModel.UIEvent.Loading -> {
                    isMangaLoading = true
                }
                is SearchViewModel.UIEvent.DataLoaded -> {
                    isMangaLoading = false
                }
                is SearchViewModel.UIEvent.ShowSnackbar -> {
                    isMangaLoading = false
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    SearchScreenContent(
        navController = navController,
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        animes = viewModel.animeList,
        mangas = viewModel.mangaList,
        isAnimeLoading = isAnimeLoading,
        isMangaLoading = isMangaLoading,
        onSearch = { keyword ->
            viewModel.onEvent(SearchEvent.SearchAniManga(keyword))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    animes: List<AniMangaListItemEntity> = listOf(),
    mangas: List<AniMangaListItemEntity> = listOf(),
    isAnimeLoading: Boolean = true,
    isMangaLoading: Boolean = true,
    onSearch: (String) -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)},
        topBar = {
            TopAppBar(
                title = {
                   // search text field
                   Box {
                       TextField(
                           value = searchQuery,
                           onValueChange = {
                               searchQuery = it
                               if (searchQuery.length > 2) {
                                   onSearch(searchQuery)
                               }
                           },
                           singleLine = true,
                           placeholder = { Text(text = "Search") },
                           textStyle = MaterialTheme.typography.bodyMedium,
                           trailingIcon = {
                               ExpandableContent(
                                   visible = searchQuery.isNotEmpty(),
                               ) {
                                   IconButton(
                                       onClick = {
                                           searchQuery = ""
                                       },
                                   ) {
                                       Icon(
                                           imageVector = Icons.Rounded.Close,
                                           contentDescription = "Erase Search",
                                       )
                                   }
                               }
                           },
                           colors = TextFieldDefaults.textFieldColors(
                               containerColor = Color.Transparent,
                               focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                               cursorColor = MaterialTheme.colorScheme.primary,
                           ),
                           modifier = modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp)
                       )
                   }
               },
               navigationIcon = {
                   // back button
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
               }
           )
       }
   ) {
       Column(
           modifier = modifier
               .padding(it)
       ) {
           Text(
                text = "${animes.size} Anime Found",
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
           )
           LazyColumn(
               modifier = modifier
                   .fillMaxWidth()
                   .fillMaxHeight(0.45f)
           ) {
               if (isAnimeLoading) {
                   items(5) {
                       AnimeListItem(isLoading = true)
                   }
               } else {
                   items(animes.size) { idx ->
                       AnimeListItem(
                           data = animes[idx],
                           isLoading = false,
                           onItemClick = {data ->
                               navController.navigate(
                                   "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/${data.title}/${data.id}"
                               )
                           },
                       )
                   }
               }
           }
           Text(
                text = "${mangas.size} Manga Found",
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
           )
           LazyColumn(
               modifier = modifier
                   .fillMaxWidth()
                   .fillMaxHeight(1f)
           ) {
               if (isMangaLoading) {
                     items(5) {
                          AnimeListItem(isLoading = true)
                     }
               } else {
                   items(mangas.size) { idx ->
                       AnimeListItem(
                           data = mangas[idx],
                           isLoading = false,
                           onItemClick = { data ->
                               navController.navigate(
                                   "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/${data.title}/${data.id}"
                               )
                           },
                       )
                   }
               }
           }
       }
   }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        SearchScreenContent(navController = rememberNavController())
    }
}
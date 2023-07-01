package com.djabaridev.anicatalog.presentation.features.all

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.home.components.MangaListItem
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun MangaSeeAllScreen(
    navController: NavController,
    viewModel: MangaSeeAllViewModel = hiltViewModel(),
) {
    val mangas = viewModel.getMangaRank.collectAsLazyPagingItems()

    MangaSeeAllContent(
        navController = navController,
        mangas = mangas,
        sortBy = viewModel.currentMangaRankType,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaSeeAllContent(
    navController: NavController = rememberNavController(),
    mangas: LazyPagingItems<AniMangaListItemEntity>? = null,
    sortBy: String = "Popular Manga",
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                title = {
                    Text(
                        text = sortBy,
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
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(top = 16.dp)
        ) {
            items(mangas?.itemCount ?: 0) { idx ->
                MangaListItem(
                    data = mangas?.get(idx),
                    isLoading = false,
                    onItemClick = { item ->
                        navController.navigate(
                            "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/${item.title}/${item.id}"
                        )
                    }
                )
            }

            when(val state = mangas?.loadState?.refresh) {
                is LoadState.Error -> {
                    item {
                        Text(
                            text = state.error.localizedMessage ?: "Unknown Error",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            MangaListItem(isLoading = true)
                        }
                    }
                }
                else -> {}
            }

            when(val state = mangas?.loadState?.append) {
                is LoadState.Error -> {
                    item {
                        Text(
                            text = state.error.localizedMessage ?: "Unknown Error",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            MangaListItem(isLoading = true)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewMangaSeeAllScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        MangaSeeAllContent()
    }
}
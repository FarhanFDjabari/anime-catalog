package com.djabaridev.anicatalog.presentation.features.all

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
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
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun AnimeSeeAllScreen(
    navController: NavController,
    viewModel: AnimeSeeAllViewModel = hiltViewModel(),
) {
    val animes = viewModel.getAnimeSeasonal.collectAsLazyPagingItems()

    AnimeSeeAllContent(
        navController = navController,
        animes = animes,
        currentSeason = viewModel.currentAnimeSeason.capitalize(Locale.current),
        currentYear = viewModel.currentAnimeYear.toString(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSeeAllContent(
    navController: NavController = rememberNavController(),
    animes: LazyPagingItems<AniMangaListItemEntity>? = null,
    currentSeason: String = "Summer",
    currentYear: String = "2023",
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                title = {
                    Text(
                        text = "$currentSeason $currentYear Anime",
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
            items(
                count = animes?.itemCount ?: 0,
            ) {idx ->
                AnimeListItem(
                    data = animes?.get(idx),
                    isLoading = false,
                    onItemClick = {item ->
                        navController.navigate(
                            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/${item.title}/${item.id}"
                        )
                    },
                )
            }

            when(val state = animes?.loadState?.refresh) {
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
                            AnimeListItem(isLoading = true)
                        }
                    }
                }
                else -> {}
            }

            when(val state = animes?.loadState?.append) {
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
                            AnimeListItem(isLoading = true)
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
fun PreviewAnimeSeeAllScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeSeeAllContent()
    }
}
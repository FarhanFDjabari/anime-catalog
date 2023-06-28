package com.djabaridev.anicatalog.presentation.features.favorite.anime

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.CoroutineScope

@Composable
fun AnimeFavoriteTab(
    navController: NavController,
    animes: List<AniMangaListItemEntity>? = emptyList(),
    draggableState: DraggableState,
    onDragStop: suspend CoroutineScope.(Float) -> Unit
) {
    if (animes?.isNotEmpty() == true) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = onDragStop,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(animes.size) {
                AnimeListItem(
                    isLoading = true,
                    onItemClick = {
                        navController.navigate(
                            "${AniCatalogNavOption.ANIME_DETAIL_SCREEN.name}/Anime Title/0"
                        )
                    }
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "No Favorite Anime",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewAnimeFavoriteTab() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeFavoriteTab(
            navController = rememberNavController(),
            draggableState = DraggableState {},
            onDragStop = {},
        )
    }
}
package com.djabaridev.anicatalog.presentation.features.favorite.manga

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
import com.djabaridev.anicatalog.presentation.features.home.components.MangaListItem
import com.djabaridev.anicatalog.presentation.navigation.AniCatalogNavOption
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.CoroutineScope

@Composable
fun MangaFavoriteTab(
    navController: NavController,
    mangas: List<AniMangaListItemEntity>? = emptyList(),
    draggableState: DraggableState,
    onDragStop: suspend CoroutineScope.(Float) -> Unit
) {
    if (mangas?.isNotEmpty() == true) {
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
            items(mangas.size) {idx ->
                MangaListItem(
                    data = mangas[idx],
                    isLoading = false,
                    onItemClick = {
                        navController.navigate(
                            "${AniCatalogNavOption.MANGA_DETAIL_SCREEN.name}/${mangas[idx].title}/${mangas[idx].id}"
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
                text = "No Favorite Manga",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewMangaFavoriteTab() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        MangaFavoriteTab(
            navController = rememberNavController(),
            draggableState = DraggableState {},
            onDragStop = {},
        )
    }
}
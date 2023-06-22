package com.djabaridev.anicatalog.presentation.features.favorite.manga

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
        item(mangas) {
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
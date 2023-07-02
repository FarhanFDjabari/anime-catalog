package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.djabaridev.anicatalog.domain.entities.AniMangaListItemEntity
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import com.djabaridev.anicatalog.utils.snakeToReadable

@Composable
fun AnimeListItem(
    data: AniMangaListItemEntity? = null,
    isLoading: Boolean = false,
    onItemClick: (AniMangaListItemEntity) -> Unit = {},
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(
                    enabled = !isLoading && data != null,
                ) {
                    if (data != null) {
                        onItemClick(data)
                    }
                },
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(show = isLoading))
            ) {
                if (!isLoading) {
                    AsyncImage(
                        model = data?.mediumPicture ?: "https://geekflare.com/wp-content/uploads/2023/03/img-placeholder.png",
                        contentDescription = data?.title ?: "",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            if (isLoading) {
                ShimmerDataLoading()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                ) {
                    Text(
                        text = data?.title ?: "-",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = data?.jpTitle ?: "-",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = data?.status?.snakeToReadable() ?: "-",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = data?.synopsis ?: "-",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Genre: ${data?.genres?.joinToString(", ") ?: "-"}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AnimeListItemPreview() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeListItem()
    }
}
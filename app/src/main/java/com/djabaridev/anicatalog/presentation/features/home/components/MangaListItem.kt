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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun MangaListItem(onItemClick: () -> Unit = {}) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onItemClick()
                },
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(shimmerBrush(show = true))
                    .clickable {
                        onItemClick()
                    }
            )
            if (true) {
                ShimmerDataLoading()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                ) {
                    Text(
                        text = "Manga Title",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Alternative Title",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Genre: Action, Romance, Comedy",
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
fun MangaListItemPreview() {
    AniCatalogThemeWrapper {
        MangaListItem()
    }
}
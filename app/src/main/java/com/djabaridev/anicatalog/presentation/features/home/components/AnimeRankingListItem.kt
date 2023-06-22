package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun AnimeRankingListItem(modifier: Modifier = Modifier, onItemClick: () -> Unit = {}) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
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
        }
    }
}

@Preview
@Composable
fun PreviewAnimeRankingListItem() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeRankingListItem()
    }
}
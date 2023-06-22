package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun MangaRankingListHeader() {
    var isMangaRankingTypeExpanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Manga For You",
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Popularity Sort Icon",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            isMangaRankingTypeExpanded = !isMangaRankingTypeExpanded
                        }
                )
                DropdownMenu(
                    expanded = isMangaRankingTypeExpanded,
                    onDismissRequest = { isMangaRankingTypeExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = { /*TODO*/ },
                    )
                    DropdownMenuItem(
                        text = { Text("Airing") },
                        onClick = { /*TODO*/ },
                    )
                    DropdownMenuItem(
                        text = { Text("Upcoming") },
                        onClick = { /*TODO*/ },
                    )
                }
                ClickableText(
                    text = AnnotatedString("See All"),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMangaRankingListHeader() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        MangaRankingListHeader()
    }
}
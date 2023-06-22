package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.R
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun AnimeRankingListHeader(modifier: Modifier = Modifier) {
    var isPopularCategoryExpanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.popular_anime_for_you),
                style = MaterialTheme.typography.titleMedium,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Popularity Sort Icon",
                    modifier = Modifier
                        .clickable {
                            isPopularCategoryExpanded = !isPopularCategoryExpanded
                        }
                )
                DropdownMenu(
                    expanded = isPopularCategoryExpanded,
                    onDismissRequest = { isPopularCategoryExpanded = false },
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
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnimeRankingList() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeRankingListHeader()
    }
}
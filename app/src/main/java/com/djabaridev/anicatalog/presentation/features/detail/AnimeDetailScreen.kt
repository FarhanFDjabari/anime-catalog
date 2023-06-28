package com.djabaridev.anicatalog.presentation.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.data.remote.responses.anime.AnimeDetailResponse
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableCard
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableText
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper


@Composable
fun AnimeDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailViewModel = hiltViewModel(),
) {
    AnimeDetailScreenContent(
        navController = navController,
        backgroundColor = viewModel.backgroundColor.value,
        animeDetail = viewModel.animeDetail.value,
        topBarTitle = viewModel.currentAnimeTitle?: "Anime Title",
        isFavorite = viewModel.isAnimeFavorite.value,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreenContent(
    navController: NavController,
    backgroundColor: Color,
    animeDetail: AnimeDetailResponse? = null,
    topBarTitle: String,
    isFavorite: Boolean = false,
    modifier: Modifier,
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.background
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = if (isFavorite)
                        Icons.Filled.BookmarkAdded
                    else Icons.Outlined.BookmarkAdd,
                    contentDescription = "Add To Favorite",
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor,
                        MaterialTheme.colorScheme.background,
                    ),
                    startY = 0f,
                    endY = 1000f,
                )
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    modifier = Modifier
                        .width(190.dp)
                        .height(220.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(show = true))
                )
            }
            // anime title
            item {
                Text(
                    text = "Anime Title",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                )
            }
            // anime alternative title
            item {
                Text(
                    text = "Alternative Title",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
            }
            // anime rating bar
            item {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.StarRate,
                        contentDescription = "Rating",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 5.dp)
                    )
                    Text(
                        text = "9.99",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            // genre
            item {
                Text(
                    text = "Action, Romance, Comedy, Drama, Slice of Life, School",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 64.dp, start = 64.dp)
                )
            }

            // synopsis header
            item {
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }
            // synopsis
            item {
                ExpandableText(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. Donec euismod, nisl eget ultricies ultrices, nisl nisl aliquam nisl, vitae aliquam nisl nisl vitae nisl. ",
                    style = MaterialTheme.typography.bodyMedium,
                    showLessStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.W600,
                    ),
                    showMoreStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.W600,
                    ),
                    textAlign = TextAlign.Justify,
                    collapsedMaxLine = 10,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 16.dp, start = 16.dp)
                )
            }
            // anime cast
            item {
                ExpandableCard(
                    title = {
                        Text(
                            text = "Cast",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(10) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(shimmerBrush(show = true))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnimeDetailScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeDetailScreenContent(
            navController = rememberNavController(),
            modifier = Modifier,
            backgroundColor = MaterialTheme.colorScheme.primary,
            topBarTitle = "Anime Title",
        )
    }
}
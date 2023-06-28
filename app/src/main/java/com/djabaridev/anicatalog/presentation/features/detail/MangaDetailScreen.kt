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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import com.djabaridev.anicatalog.data.remote.responses.manga.MangaDetailResponse
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableCard
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableText
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper
import kotlinx.coroutines.launch

@Composable
fun MangaDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MangaDetailViewModel = hiltViewModel(),
) {
    var isLoading by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collect {
            when (it) {
                is MangaDetailViewModel.UIEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = it.message,
                            actionLabel = "Ok",
                        )
                    }
                }
                is MangaDetailViewModel.UIEvent.Loading -> {
                    isLoading = true
                }
                is MangaDetailViewModel.UIEvent.DataLoaded -> {
                    isLoading = false
                }
            }
        }
    }

    MangaDetailScreenContent(
        navController = navController,
        backgroundColor = viewModel.backgroundColor.value,
        mangaDetail = viewModel.mangaDetail.value,
        topBarTitle = viewModel.currentMangaTitle?: "Manga Title",
        isFavorite = viewModel.isMangaFavorite.value,
        isLoading = isLoading,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetailScreenContent(
    navController: NavController,
    backgroundColor: Color,
    mangaDetail: MangaDetailResponse? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    topBarTitle: String,
    isFavorite: Boolean = false,
    isLoading: Boolean = true,
    modifier: Modifier,
) {
    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
            if (!isLoading) {
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
                        .background(shimmerBrush(show = isLoading))
                ) {
                    if (mangaDetail != null) {
                        AsyncImage(
                            model = mangaDetail.main_picture.large,
                            contentDescription = mangaDetail.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
            // anime title
            item {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(25.dp)
                            .background(shimmerBrush(show = true))
                    )
                } else {
                    Text(
                        text = mangaDetail?.title ?: "",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                }
            }
            // anime alternative title
            item {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(20.dp)
                            .padding(top = 5.dp)
                            .background(shimmerBrush(show = true))
                    )
                } else {
                    Text(
                        text = mangaDetail?.alternative_titles?.ja ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                }
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
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(35.dp)
                                .background(shimmerBrush(show = true))
                        )
                    } else {
                        Text(
                            text = "${mangaDetail?.mean ?: 0.0}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            // genre
            item {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(20.dp)
                            .padding(top = 5.dp)
                            .background(shimmerBrush(show = true))
                    )
                } else {
                    Text(
                        text = mangaDetail?.genres?.joinToString(", ") { genre -> genre.name } ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 64.dp, start = 64.dp)
                    )
                }
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
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 5.dp, end = 16.dp, start = 16.dp)
                            .background(shimmerBrush(show = true))
                    )
                } else {
                    ExpandableText(
                        text = mangaDetail?.synopsis ?: "",
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
            }
            // anime cast
            item {
                ExpandableCard(
                    title = {
                        Text(
                            text = "Related Manga",
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
                        items(mangaDetail?.related_manga?.size ?: 0) {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(130.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(shimmerBrush(show = isLoading))
                            ) {
                                AsyncImage(
                                    model = mangaDetail?.related_manga?.get(it)?.node?.main_picture?.medium,
                                    contentDescription = mangaDetail?.related_manga?.get(it)?.node?.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMangaDetailScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        MangaDetailScreenContent(
            navController = rememberNavController(),
            modifier = Modifier,
            backgroundColor = MaterialTheme.colorScheme.primary,
            topBarTitle = "Manga Title"
        )
    }
}
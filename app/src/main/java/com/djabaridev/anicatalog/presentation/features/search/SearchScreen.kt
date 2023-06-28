package com.djabaridev.anicatalog.presentation.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.djabaridev.anicatalog.presentation.features.detail.components.ExpandableContent
import com.djabaridev.anicatalog.presentation.features.home.components.AnimeListItem
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreenContent(navController = navController, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var searchQuery by remember { mutableStateOf("") }

   Scaffold(
       topBar = {
           TopAppBar(
               title = {
                   // search text field
                   Box {
                       TextField(
                           value = searchQuery,
                           onValueChange = {
                               searchQuery = it
                           },
                           singleLine = true,
                           placeholder = { Text(text = "Search") },
                           textStyle = MaterialTheme.typography.bodyMedium,
                           trailingIcon = {
                               ExpandableContent(
                                   visible = searchQuery.isNotEmpty(),
                               ) {
                                   IconButton(
                                       onClick = {
                                           searchQuery = ""
                                       },
                                   ) {
                                       Icon(
                                           imageVector = Icons.Rounded.Close,
                                           contentDescription = "Erase Search",
                                       )
                                   }
                               }
                           },
                           colors = TextFieldDefaults.textFieldColors(
                               containerColor = Color.Transparent,
                               focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                               cursorColor = MaterialTheme.colorScheme.primary,
                           ),
                           modifier = modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp)
                       )
                   }
               },
               navigationIcon = {
                   // back button
                   IconButton(
                       onClick = {
                           navController.navigateUp()
                       },
                   ) {
                       Icon(
                           imageVector = Icons.Rounded.ArrowBack,
                           contentDescription = "Back Icon",
                           tint = MaterialTheme.colorScheme.primary
                       )
                   }
               }
           )
       }
   ) {
       Column(
           modifier = modifier
               .padding(it)
       ) {
           Text(
                text = "5 Anime Found",
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
           )
           LazyColumn(
               modifier = modifier
                   .fillMaxWidth()
                   .fillMaxHeight(0.45f)
           ) {
                items(5) {
                     AnimeListItem(isLoading = true)
                }
           }
           Text(
                text = "5 Manga Found",
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
           )
           LazyColumn(
               modifier = modifier
                   .fillMaxWidth()
                   .fillMaxHeight(1f)
           ) {
               items(5) {
                   AnimeListItem(isLoading = true)
               }
           }
       }
   }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        SearchScreenContent(navController = rememberNavController())
    }
}
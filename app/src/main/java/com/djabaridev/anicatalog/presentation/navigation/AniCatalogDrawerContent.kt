package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.R
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun<T: Enum<AniCatalogNavOption>> AniCatalogDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AniCatalogDrawerItemInfo<T>>,
    onMenuClick: (T: Enum<AniCatalogNavOption>) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet(
        drawerShape = RectangleShape,
        modifier = Modifier.width(200.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.myanimelist_launcher_logo),
                contentDescription = "MyAnimeList Logo",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(menuItems) { item ->
                    AniCatalogDrawerItem(item = item) {
                        coroutineScope.launch {
                            drawerState.close()
                        }

                        onMenuClick(it)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                AniCatalogDrawerItem(
                    item = AniCatalogDrawerItemInfo(
                        AniCatalogNavOption.LOGOUT,
                        R.string.drawer_logout,
                        Icons.Outlined.Logout,
                        R.string.drawer_logout_description,
                    ),
                    contentColor = MaterialTheme.colorScheme.error,
                ) {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onMenuClick(it)
                }
            }
        }
    }
}
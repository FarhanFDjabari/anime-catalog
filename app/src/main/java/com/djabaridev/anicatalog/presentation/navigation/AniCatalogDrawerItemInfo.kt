package com.djabaridev.anicatalog.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.djabaridev.anicatalog.R

data class AniCatalogDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
)

object DrawerParams {
    val drawerButtons = listOf(
        AniCatalogDrawerItemInfo(
            AniCatalogNavOption.HOME_SCREEN,
            R.string.drawer_home,
            Icons.Outlined.Home,
            R.string.drawer_home_description,
        ),
        AniCatalogDrawerItemInfo(
            AniCatalogNavOption.FAVORITE_SCREEN,
            R.string.drawer_favorite,
            Icons.Outlined.Bookmarks,
            R.string.drawer_favorite_description,
        ),
    )
}
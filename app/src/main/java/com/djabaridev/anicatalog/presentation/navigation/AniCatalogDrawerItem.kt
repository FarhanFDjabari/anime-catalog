package com.djabaridev.anicatalog.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun <T> AniCatalogDrawerItem(
    item: AniCatalogDrawerItemInfo<T>,
    contentColor: Color? = null,
    onClick: (options: T) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick(item.drawerOption) },
        shape = RoundedCornerShape(10),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = stringResource(id = item.contentDescription),
                tint = contentColor?: MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = contentColor?: MaterialTheme.colorScheme.primary,
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}
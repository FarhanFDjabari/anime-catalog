package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeasonDropdownButton(
    modifier: Modifier = Modifier,
    value: String,
    onDropdownItemClick: (String) -> Unit = {},
) {
    val seasons = arrayOf("summer", "spring", "fall", "winter")
    var isSeasonExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isSeasonExpanded,
        onExpandedChange = {
            isSeasonExpanded = !isSeasonExpanded
        },
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(text = "Season") },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSeasonExpanded) },
            modifier = modifier
                .defaultMinSize(minHeight = 56.dp)
                .menuAnchor()
                .clickable {  isSeasonExpanded = !isSeasonExpanded  }
        )
        ExposedDropdownMenu(
            expanded = isSeasonExpanded,
            onDismissRequest = {
                isSeasonExpanded = false
            },
        ) {
            seasons.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.capitalize(Locale.current)) },
                    onClick = {
                        onDropdownItemClick(item)
                        isSeasonExpanded = false
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSeasonDropdownButton() {
   AniCatalogThemeWrapper(isDarkMode = false) {
       SeasonDropdownButton(value = "spring")
   }
}
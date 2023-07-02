package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSeasonalListHeader(
    modifier: Modifier = Modifier,
    selectedSeason: String = "summer",
    selectedYear: String = "2023",
    onFilterChange: (String, Int) -> Unit = { _, _ -> },
    onSeeAll: (String, String) -> Unit = {_, _ -> },
) {
    var selectedSeason by remember { mutableStateOf(selectedSeason) }
    var selectedYear by remember { mutableStateOf(selectedYear) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Anime on Season",
                style = MaterialTheme.typography.titleMedium,
            )
            ClickableText(
                text = AnnotatedString("See All"),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = {onSeeAll(selectedSeason, selectedYear)}
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SeasonDropdownButton(
                value = selectedSeason.capitalize(Locale.current),
                onDropdownItemClick = { item ->
                    if (selectedSeason != item) {
                        selectedSeason = item
                        if (selectedYear.isNotEmpty() && selectedSeason.isNotEmpty()) {
                            onFilterChange(selectedSeason, selectedYear.toInt())
                        }
                    }
                },
                modifier = Modifier
                    .width(160.dp)
                    .weight(1f, true)
            )
            OutlinedTextField(
                value = selectedYear,
                onValueChange = {
                    if (it.length <= 4) {
                        selectedYear = it
                        if (selectedYear.length > 3 && selectedSeason.isNotEmpty()) {
                            onFilterChange(selectedSeason, selectedYear.toInt())
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                placeholder = { Text(text = "Year") },
                shape = RoundedCornerShape(10.dp),
                trailingIcon = { Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = "Year Selector"
                )},
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 56.dp)
            )
        }
    }
}

@Preview
@Composable
fun AnimeSeasonalListPreview() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        AnimeSeasonalListHeader()
    }
}
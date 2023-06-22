package com.djabaridev.anicatalog.presentation.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.components.shimmerBrush
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper

@Composable
fun ShimmerDataLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(15.dp)
                .padding(bottom = 5.dp, top = 5.dp)
                .background(shimmerBrush(show = true))
        )
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(15.dp)
                .padding(bottom = 5.dp)
                .background(shimmerBrush(show = true))
        )
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(bottom = 5.dp)
                .background(shimmerBrush(show = true))
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .padding(bottom = 10.dp)
                .background(shimmerBrush(show = true))
        )
    }
}

@Preview
@Composable
fun PreviewShimmerDataLoading() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        ShimmerDataLoading()
    }
}
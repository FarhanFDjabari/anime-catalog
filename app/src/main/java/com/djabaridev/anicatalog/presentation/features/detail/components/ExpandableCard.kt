package com.djabaridev.anicatalog.presentation.features.detail.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.djabaridev.anicatalog.presentation.theme.AniCatalogThemeWrapper


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    expandedColor: Color = MaterialTheme.colorScheme.surface,
    collapsedColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onCardArrowClick: () -> Unit = {},
    animDuration: Int = 450,
    expanded: Boolean = false,
    expandedContent: @Composable () -> Unit = {},
) {
    var isExpanded by remember {
        mutableStateOf(expanded)
    }
    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = animDuration)
    }, label = "bgColorTransition") {
        if (isExpanded) expandedColor else collapsedColor
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = animDuration)
    }, label = "elevationTransition") {
        if (isExpanded) 4.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = animDuration,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        if (isExpanded) 10.dp else 10.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = animDuration)
    }, label = "rotationDegreeTransition") {
        if (isExpanded) 180f else 0f
    }
    val contentColour = remember {
        contentColor
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor,
            contentColor = contentColour,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = cardElevation
        ),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    contentColor = contentColour,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                title()
            }
            ExpandableContent(
                visible = isExpanded,
                expandedContent = expandedContent,
            )
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = {},
        content = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
                tint = contentColor,
            )
        },
        modifier = modifier
    )
}

@Composable
fun ExpandableContent(
    visible: Boolean = true,
    expandedContent: @Composable () -> Unit,
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(500)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(500)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(500)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(500)
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        expandedContent()
    }
}

@Preview
@Composable
fun PreviewExpandableCard() {
    AniCatalogThemeWrapper(isDarkMode = false) {
        ExpandableCard(
            title = {
                Text(
                    text = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            },
            expandedColor = MaterialTheme.colorScheme.surface,
            collapsedColor = MaterialTheme.colorScheme.surface,
            onCardArrowClick = {  },
            expanded = false,
            expandedContent = {
                Column(modifier = Modifier.padding(8.dp)) {
                    Spacer(modifier = Modifier.heightIn(100.dp))
                    Text(
                        text = "Expandable content here",
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}
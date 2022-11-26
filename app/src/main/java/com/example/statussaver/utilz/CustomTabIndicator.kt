package com.example.statussaver.utilz

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.zIndex

@Composable
fun CustomTabIndicator(
    currentTabPosition: TabPosition,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .zIndex(1f)
            .fillMaxSize()
            .customTabIndicatorOffset(currentTabPosition)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
    )
}

fun Modifier.customTabIndicatorOffset(currentTabPosition: TabPosition): Modifier =
    composed {
        val currentTabWidth = currentTabPosition.width
        val indicatorOffset by animateDpAsState(
            targetValue = currentTabPosition.left,
            animationSpec = tween()
        )
        fillMaxSize()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
    }
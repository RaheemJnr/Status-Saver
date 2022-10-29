package com.example.statussaver.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBarUI(
    navController: NavHostController
) {
    Column {
        //bottom nav
        BottomAppBar(
            modifier = Modifier
                .height(60.dp),
            backgroundColor = Color.White,
            elevation = 22.dp,
        ) {
            BottomNav(
                navController = navController,
            )
        }
    }
}
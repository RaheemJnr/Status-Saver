package com.example.statussaver.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.statussaver.navigation.MainScreenNavigation
import com.example.statussaver.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    /*** main viewModel */
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            Surface(elevation = 12.dp) {
                BottomBarUI(navController)
            }
        },
        ) {
        MainScreenNavigation(navController)
    }
}
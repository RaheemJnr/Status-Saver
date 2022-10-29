package com.example.statussaver.navigation


import MainScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.statussaver.ui.screen.saved_files.SavedFileScreen
import com.example.statussaver.ui.screen.whatsapp.Whatsapp
import com.example.statussaver.ui.screen.whatsapp_business.WABusiness
import com.example.statussaver.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainScreenNavigation(
    navController: NavHostController,
) {
    // val mainViewModel = hiltViewModel<MainViewModel>()
    val mainViewModel = MainViewModel()
    NavHost(navController, startDestination = MainScreen.WABusiness.route) {
        //local
        composable(MainScreen.WABusiness.route) {
            WABusiness(
                mainViewModel
            )
        }
        //online
        composable(MainScreen.Whatsapp.route) {
            Whatsapp(mainViewModel = mainViewModel)
        }
        composable(MainScreen.SavedFile.route) {
            SavedFileScreen()
        }
    }

}


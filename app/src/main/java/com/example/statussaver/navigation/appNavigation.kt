package com.example.statussaver.navigation


import MainScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.statussaver.ui.screen.whatsapp_business.LocalMusicScreen
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
  //  val songListViewModel = hiltViewModel<SongListScreenViewModel>()
    NavHost(navController, startDestination = MainScreen.Local.route!!) {
        //local
        composable(MainScreen.Local.route) {
            LocalMusicScreen(
            )
        }
//        //online
//        composable(MainScreen.Online.route!!) {
//            OnlineMusicScreen()
//        }


    }

}


package com.example.statussaver.ui.components

import MainScreen
import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@SuppressLint("MutableCollectionMutableState")
@ExperimentalAnimationApi
@Composable
fun BottomNav(navController: NavController) {

    val dimension by remember { mutableStateOf(arrayListOf(35, 35, 35)) }
    HomeBottomItem(dimension, navController)
}

@Composable
private fun HomeBottomItem(
    dimension: ArrayList<Int>,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation(
        modifier = Modifier
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
            .height(100.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        items.forEach {
            BottomNavigationItem(
                alwaysShowLabel = true,
                modifier = Modifier,
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .width(dimension[it.index].dp)
                            .height(dimension[it.index].dp)
                            .animateContentSize(),
                    )
                },
                label = {
                    Text(
                        text = it.title,
                        color = Color.LightGray
                    )
                },
                //
                selected = currentRoute == it.route,
                onClick = {
                    it.route.let { destination ->
                        navController.navigate(destination) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reSelecting the same item
                            launchSingleTop = true
                            // Restore state when reSelecting a previously selected item
                            restoreState = true
                        }
                    }
                    dimension.forEachIndexed { index, _ ->
                        if (index == it.index)
                            dimension[index] = 36
                        else
                            dimension[index] = 35
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Green,
            )
        }
    }
}


val items = listOf(MainScreen.WABusiness, MainScreen.Whatsapp, MainScreen.SavedFile)
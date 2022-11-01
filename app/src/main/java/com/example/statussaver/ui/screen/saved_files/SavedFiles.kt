package com.example.statussaver.ui.screen.saved_files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SavedFileScreen(
    mainViewModel: MainViewModel,
) {
    val savedStatus = mainViewModel.savedStatus.observeAsState()
    mainViewModel.getSavedFiles()
    val isRefreshing = mainViewModel.isRefreshing


    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
            onRefresh = {
                mainViewModel.refresh()
                mainViewModel.getWABusinessStatusImage()
                mainViewModel.getWhatsappStatusImage()
            },
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                savedStatus.value?.let { list ->
                    items(
                        items = list,
                        key = {
                            it.path
                        },
                        contentType = {
                            it.path
                        }
                    ) {
                        ImageLayout(status = it) {
//                            Common.copyFile(status = it, context = context)
                        }
                    }
                }
            }
        }
    }

}
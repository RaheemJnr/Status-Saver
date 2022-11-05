package com.example.statussaver.ui.screen.whatsapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.statussaver.model.Status
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.ui.components.VideoLayout
import com.example.statussaver.utilz.Common
import com.example.statussaver.utilz.TabItems
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerWhatsapp(
    mainViewModel: MainViewModel,
    pagerState: PagerState,
    scope: CoroutineScope,
    errorMessage: State<String?>,
    isRefreshing: StateFlow<Boolean>,
    imageStatus: State<List<Status>?>,
    videoStatus: State<List<Status>?>
) {
    val tabsTitles =
        remember { listOf(TabItems("Images"), TabItems("Videos")) }
    //
    val context = LocalContext.current

    TabRowComposable(pagerState, tabsTitles, scope)
    HorizontalPager(
        count = tabsTitles.size,
        state = pagerState,
    ) { page ->
        when (page) {
            //image
            0 -> {
                if (errorMessage.value?.isNotEmpty() == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${errorMessage.value}")
                    }
                } else {
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
                                imageStatus.value?.let { list ->
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
                                            Common.saveFile(status = it, context = context)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            //video
            1 -> {
                if (errorMessage.value?.isNotEmpty() == true) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${errorMessage.value}")
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
                            onRefresh = {
                                mainViewModel.refresh()
                                mainViewModel.getWABusinessStatusVideo()
                            },
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 128.dp)
                            ) {
                                videoStatus.value?.let { list ->
                                    items(
                                        items = list,
                                        key = {
                                            it.path
                                        },
                                        contentType = {
                                            it.path
                                        }
                                    ) {
                                        VideoLayout(status = it) {
                                            Common.saveFile(status = it, context = context)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun TabRowComposable(
    pagerState: PagerState,
    tabsTitles: List<TabItems>,
    scope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .height(6.dp)
                    .padding(horizontal = 48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color.Black.copy(alpha = 0.6f)
            )
        },
        backgroundColor = Color.White,
        divider = {
        }
    ) {
        // Add tabs for all of our pages
        tabsTitles.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title.value) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        }
    }
}
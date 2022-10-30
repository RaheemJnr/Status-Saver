package com.example.statussaver.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.statussaver.model.Status
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
fun LocalTabLayout(
    mainViewModel: MainViewModel,
    pagerState: PagerState,
    scope: CoroutineScope,
    isRefreshing: StateFlow<Boolean>,
    imageStatus: State<List<Status>?>,
    videoStatus: State<List<Status>?>
) {
    val tabsTitles =
        remember { listOf(TabItems("Images"), TabItems("Videos")) }

    TabRowComposable(pagerState, tabsTitles, scope)
    HorizontalPager(
        count = tabsTitles.size,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
                        onRefresh = {
                            mainViewModel.refresh()
                            mainViewModel.getWABusinessStatusImage()
                        },
                    ) {
                        LazyColumn {

                            imageStatus.value?.let { list ->
                                items(
                                    items = list,
                                    key = {
                                        it.path
                                    }
                                ) {
                                    Text(
                                        text = "$it",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                   // ImageLayout(status = it)
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
                    onRefresh = {
                        mainViewModel.refresh()
                        mainViewModel.getWABusinessStatusVideo()
                    },
                ) {
                    LazyColumn {
                        videoStatus.value?.let { list ->
                            items(
                                items = list,
                                key = {
                                    it.path
                                }
                            ) {
                                Text(
                                    text = "$it",
                                    modifier = Modifier.fillMaxSize()
                                )
                                ImageLayout(status = it)
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

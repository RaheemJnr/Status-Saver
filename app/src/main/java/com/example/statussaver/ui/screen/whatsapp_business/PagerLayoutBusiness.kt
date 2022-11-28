package com.example.statussaver.ui.screen.whatsapp_business


import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.statussaver.R
import com.example.statussaver.model.Status
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.ui.components.VideoLayout
import com.example.statussaver.ui.theme.Dimens
import com.example.statussaver.utilz.*
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerBusiness(
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
    val context = (LocalContext.current) as Activity

    TabRowComposable(
        pagerState,
        tabsTitles,
        scope,
        modifier = Modifier.padding(horizontal = Dimens.MediumPadding.size),
    )
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
                                modifier = Modifier.padding(horizontal = 2.dp),
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
                                        ImageLayout(
                                            status = it,
                                            touchImageResource = R.drawable.download_icon,
                                            onViewClicked = {
                                                viewImage(context, it)
                                            },
                                            onSaveClicked = {
                                                Common.saveFile(status = it, context = context)
                                            }
                                        )
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
                                modifier = Modifier.padding(horizontal = 2.dp),
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
                                        VideoLayout(
                                            status = it,
                                            touchImageResource = R.drawable.download_icon,
                                            onSaveClicked = {
                                                Common.saveFile(status = it, context = context)
                                            },
                                            onViewClicked = {
                                                viewImage(context, it)
                                            }
                                        )
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
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    TabRow(
        modifier = modifier.width(250.dp),
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            CustomTabIndicator(currentTabPosition = tabPositions[pagerState.currentPage])
        },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onBackground,
        divider = {}
    ) {
        // Add tabs for all of our pages
        tabsTitles.forEachIndexed { index, title ->
            TabItem(
                title = title.value,
                textColor = getTabColor(
                    tabPage = pagerState,
                    selectedTabPage = index

                ), onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(Dimens.MediumPadding.size))
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun getTabColor(
    tabPage: PagerState,
    selectedTabPage: Int,
): Color =
    if (tabPage.currentPage == selectedTabPage) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onBackground
            .copy(alpha = .6f)
    }
package com.example.statussaver.ui.screen.whatsapp

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.statussaver.model.UIDataState
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.ui.components.VideoLayout
import com.example.statussaver.ui.screen.whatsapp_business.LoaderDialog
import com.example.statussaver.ui.screen.whatsapp_business.getTabColor
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
fun PagerWhatsapp(
    mainViewModel: MainViewModel,
    pagerState: PagerState,
    scope: CoroutineScope,
    isRefreshing: StateFlow<Boolean>,
    imageStatus: UIDataState,
    videoStatus: UIDataState
) {
    val tabsTitles =
        remember { listOf(TabItems("Images"), TabItems("Videos")) }
    //
    val context = (LocalContext.current) as Activity

    TabRowComposable(
        pagerState,
        tabsTitles,
        scope,
        modifier = Modifier.padding(horizontal = Dimens.MediumPadding.size)
    )
    HorizontalPager(
        count = tabsTitles.size,
        state = pagerState,
    ) { page ->
        when (page) {
            //image
            0 -> {

                when (imageStatus) {
                    UIDataState.Loading -> {
                        LoaderDialog()
                    }
                   is UIDataState.Success -> {
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
                                   items(
                                       items = imageStatus.feed.status,
                                       key = {
                                           it.path
                                       },
                                       contentType = {
                                           it.path
                                       }
                                   ) {
                                       ImageLayout(
                                           status = it,
                                           saveImageResource = R.drawable.download_icon,
                                           viewImageResource = R.drawable.view,
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
                    is UIDataState.Failed -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${imageStatus.feed.errorMessage}")
                        }
                    }
                }

            }
            //video
            1 -> {
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
                                    VideoLayout(
                                        status = it,
                                        touchImageResource = R.drawable.download_icon,
                                        viewImageResource = R.drawable.view,
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
                //   }

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
                ),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        }
    }
}

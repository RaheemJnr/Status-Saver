package com.example.statussaver.ui.screen.whatsapp_business


import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.statussaver.R
import com.example.statussaver.model.UIDataState
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
        modifier = Modifier.padding(horizontal = Dimens.MediumPadding.size),
    )
    HorizontalPager(
        count = tabsTitles.size,
        state = pagerState,
    ) { page ->
        when (page) {
            //image
            0 -> {
                when (imageStatus) {
                    is UIDataState.Loading -> {
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
//                if (errorMessage.value?.isNotEmpty() == true) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(text = "${errorMessage.value}")
//                    }
//                } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
                        onRefresh = {
                            mainViewModel.refresh()
                            mainViewModel.getWABusinessStatusVideo()
                        },
                    ) {
                        val scrollableState = rememberLazyGridState()
                        LazyVerticalGrid(
                            modifier = Modifier.padding(horizontal = 2.dp),
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            state = scrollableState
                        ) {
                            when (videoStatus) {
                                is UIDataState.Loading -> Unit
                                is UIDataState.Success -> {
                                    items(
                                        items = videoStatus.feed.status,
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
                                            onSaveClicked = {
                                                Common.saveFile(status = it, context = context)
                                            },
                                            onViewClicked = {
                                                viewImage(context, it)
                                            }
                                        )
                                    }
                                }
                                is UIDataState.Failed -> {
                                    Toast.makeText(
                                        context,
                                        "${videoStatus.feed.errorMessage}",
                                        Toast.LENGTH_SHORT
                                    ).show()
//                                        Box(
//                                            modifier = Modifier.fillMaxSize(),
//                                            contentAlignment = Alignment.Center
//                                        ) {
//                                            Text(text = "${errorMessage.value}")
//                                        }
                                }
                            }
                        }
                    }
                }
                //  }

            }
        }
    }
}

@Composable
fun LoaderDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
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
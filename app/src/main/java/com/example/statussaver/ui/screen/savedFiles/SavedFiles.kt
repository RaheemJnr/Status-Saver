package com.example.statussaver.ui.screen.savedFiles

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.statussaver.R
import com.example.statussaver.model.UIDataState
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.ui.components.VideoLayout
import com.example.statussaver.ui.screen.whatsapp_business.LoaderDialog
import com.example.statussaver.utilz.StatusPageHeading
import com.example.statussaver.utilz.shareFileIntentForImage
import com.example.statussaver.utilz.shareFileIntentForVideo
import com.example.statussaver.utilz.viewImage
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun SavedFileScreen(
    mainViewModel: MainViewModel,
) {
    val savedStatus = mainViewModel.savedStatus.observeAsState(initial = UIDataState.Loading)
    mainViewModel.getSavedFiles()
    val isRefreshing = mainViewModel.isRefreshing
    val context = (LocalContext.current) as Activity
    Scaffold(
        modifier = Modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .statusBarsPadding()
        ) {
            StatusPageHeading(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Saved Files",
                extraItems = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
            when (savedStatus.value) {
                is UIDataState.Loading -> {
                    LoaderDialog()
                }
                is UIDataState.Success -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

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
                                items(
                                    items = (savedStatus.value as UIDataState.Success).feed.status,
                                    key = {
                                        it.path
                                    },
                                    contentType = {
                                        it.path
                                    }
                                ) {
                                    if (!it.isVideo && it.title.endsWith(".jpg")) {
                                        ImageLayout(
                                            status = it,
                                            saveImageResource = R.drawable.share,
                                            viewImageResource = R.drawable.view,
                                            onSaveClicked = {
                                                shareFileIntentForImage(
                                                    status = it,
                                                    context = context
                                                )
                                            },
                                            onViewClicked = {
                                                viewImage(context, it)
                                            }
                                        )
                                    } else {
                                        VideoLayout(
                                            status = it,
                                            touchImageResource = R.drawable.share,
                                            viewImageResource = R.drawable.view,
                                            onSaveClicked = {
                                                shareFileIntentForVideo(
                                                    status = it,
                                                    context = context
                                                )
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
                is UIDataState.Failed -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${(savedStatus.value as UIDataState.Failed).feed.errorMessage}")
                    }
                }
            }


        }

    }


}

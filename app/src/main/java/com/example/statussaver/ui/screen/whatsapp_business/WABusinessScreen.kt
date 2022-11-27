package com.example.statussaver.ui.screen.whatsapp_business

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statussaver.utilz.StatusPageHeading
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun WABusiness(
    mainViewModel: MainViewModel
) {
    //context
    val imageStatus = mainViewModel.waBusinessImageStatus.observeAsState()
    val videoStatus = mainViewModel.waBusinessVideoStatus.observeAsState()
    val errorMessage = mainViewModel.errorMessageBusiness.observeAsState()
    val isRefreshing = mainViewModel.isRefreshing
    mainViewModel.getWABusinessStatusImage()
    mainViewModel.getWABusinessStatusVideo()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    //
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 1.dp, end = 1.dp, bottom = 62.dp)
    ) {
        Scaffold { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .statusBarsPadding()
            ) {
                StatusPageHeading(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "Status Saver",
                    extraItems = {
                        IconButton(
                            modifier = Modifier.background(
                                MaterialTheme.colors.surface,
                                CircleShape
                            ),
                            onClick = {},
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colors.onSurface

                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                //
                PagerBusiness(
                    mainViewModel = mainViewModel,
                    pagerState = pagerState,
                    scope = scope,
                    errorMessage = errorMessage,
                    isRefreshing = isRefreshing,
                    imageStatus = imageStatus,
                    videoStatus = videoStatus
                )
            }

        }

    }
}


@Preview
@Composable
fun LocalMusicScreenPreview() {
    // LocalMusicScreen()
}





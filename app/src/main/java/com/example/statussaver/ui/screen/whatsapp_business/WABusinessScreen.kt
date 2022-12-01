package com.example.statussaver.ui.screen.whatsapp_business

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statussaver.model.UIDataState
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
    val imageStatus by mainViewModel.waBusinessImageStatus.observeAsState()
    val videoStatus by mainViewModel.waBusinessVideoStatus.observeAsState()
  //  val errorMessage by mainViewModel.errorMessageBusiness.observeAsState()
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
                    title = "Whatsapp Business",
                    extraItems = {
                        IconButton(
                            modifier = Modifier.background(
                                MaterialTheme.colors.surface.copy(alpha = 0.6f),
                                CircleShape
                            ),
                            onClick = {},
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "MoreVert",
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
                    isRefreshing = isRefreshing,
                    imageStatus = imageStatus ?: UIDataState.Loading,
                    videoStatus = videoStatus ?: UIDataState.Loading
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





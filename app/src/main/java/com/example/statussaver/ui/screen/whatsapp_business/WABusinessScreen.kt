package com.example.statussaver.ui.screen.whatsapp_business

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Scaffold(
            topBar = { }
        ) { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {

                Spacer(modifier = Modifier.height(4.dp))
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





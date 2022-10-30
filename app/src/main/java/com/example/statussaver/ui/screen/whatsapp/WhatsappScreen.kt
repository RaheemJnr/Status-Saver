package com.example.statussaver.ui.screen.whatsapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statussaver.ui.components.LocalTabLayout
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Whatsapp(
    mainViewModel: MainViewModel
) {
    //context
    val imageStatus = mainViewModel.whatsappImageStatus.observeAsState()
    val videoStatus = mainViewModel.whatsappVideoStatus.observeAsState()
    val isRefreshing = mainViewModel.isRefreshing
    Log.d("whatsapp_image", "${imageStatus.value}")
    Log.d("whatsapp_video", "${videoStatus.value}")
    mainViewModel.getWhatsappStatus()
    mainViewModel.getWhatsappStatusVideo()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    //root composable
    Column(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { }
        ) { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {

                Spacer(modifier = Modifier.height(4.dp))
                //
                LocalTabLayout(
                    mainViewModel = mainViewModel,
                    pagerState = pagerState,
                    isRefreshing = isRefreshing,
                    scope = scope,
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





package com.example.statussaver.ui.screen.whatsapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
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
fun Whatsapp(
    mainViewModel: MainViewModel
) {
    //context
    val imageStatus by mainViewModel.whatsappImageStatus.observeAsState()
    val videoStatus by mainViewModel.whatsappVideoStatus.observeAsState()
    //val errorMessage = mainViewModel.errorMessage.observeAsState()
    mainViewModel.getWhatsappStatusImage()
    mainViewModel.getWhatsappStatusVideo()

    Log.i("Whatsapp", "$imageStatus")
    Log.i("Whatsapp", "$videoStatus")

    val isRefreshing = mainViewModel.isRefreshing
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    //root composable
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 62.dp)
    ) {
        Scaffold(
            topBar = {

            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .statusBarsPadding()
            ) {
                StatusPageHeading(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "Whatsapp",
                    extraItems = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
                PagerWhatsapp(
                    mainViewModel = mainViewModel,
                    pagerState = pagerState,
                    isRefreshing = isRefreshing,
                    scope = scope,
                    imageStatus = imageStatus  ?: UIDataState.Loading,
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





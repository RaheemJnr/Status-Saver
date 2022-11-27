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
    val imageStatus = mainViewModel.whatsappImageStatus.observeAsState()
    val videoStatus = mainViewModel.whatsappVideoStatus.observeAsState()
    val errorMessage = mainViewModel.errorMessage.observeAsState()
    mainViewModel.getWhatsappStatusImage()
    mainViewModel.getWhatsappStatusVideo()

    Log.d("Whatsapp", "$imageStatus")
    Log.d("Whatsapp", "$videoStatus")

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
                    extraItems = {
//                        IconButton(
//                            modifier = Modifier.background(
//                                MaterialTheme.colors.surface,
//                                CircleShape
//                            ),
//                            onClick = {},
//                        ) {
////                            Icon(
////                                imageVector = Icons.Rounded.MoreVert,
////                                contentDescription = "MoreVert",
////                                tint = MaterialTheme.colors.onSurface
////
////                            )
//                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                PagerWhatsapp(
                    mainViewModel = mainViewModel,
                    pagerState = pagerState,
                    isRefreshing = isRefreshing,
                    errorMessage = errorMessage,
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





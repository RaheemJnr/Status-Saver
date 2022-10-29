package com.example.statussaver.ui.screen.whatsapp_business

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statussaver.ui.components.LocalTabLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun LocalMusicScreen(
) {
    //context
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    //root composable
    Column(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {  }
        ) { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {

                Spacer(modifier = Modifier.height(4.dp))
                //
                LocalTabLayout(
                    pagerState = pagerState,
                    scope = scope,
                    audios = ,
                    context = context,
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





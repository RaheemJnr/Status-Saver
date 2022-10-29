package com.example.statussaver.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.statussaver.model.Status
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LocalTabLayout(
    pagerState: PagerState,
    scope: CoroutineScope,
    audios: State<List<Status>?>,
    context: Context,
) {
    val tabsTitles =
        remember { listOf(TabItems("GbWhatsApp"), TabItems("WhatsApp"), TabItems("Saved Files")) }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .height(6.dp)
                    .padding(horizontal = 48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color.Black.copy(alpha = 0.6f)
            )
        },
        backgroundColor = Color.White,
        divider = {
        }
    ) {
        // Add tabs for all of our pages
        tabsTitles.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title.value) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
            )
        }
    }

    HorizontalPager(
        count = tabsTitles.size,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                //whatsapp business
                Box(modifier = Modifier)
                {
                    LazyColumn {
                        audios.value?.let { list ->
//                            items(
//                                items = list,
//                                key = {
//                                    it.id
//                                }
//                            ) { item: Songs ->
//                                SongListItem(
//                                    songTitle = item.title,
//                                    songArtist = item.artist,
//                                    songAlbum = item.album,
//                                    context = context
//                                ) {
//                                    mainViewModel.playMediaId(item.id)
//                                    // mainViewModel.isCollapsed.value = false
//                                }
//                            }
                        }

                    }
                }
            }
            1 -> {
                //whatsapp
                LazyColumn {
//                    audios.value?.let { itemm ->
//                        items(
//                            items = itemm,
//                            key = {
//                                it.id
//                            }
//                        ) { item: Songs ->
//                            AlbumsItem(
//                                context = context,
//                                albumTitle = item.album,
//                                albumArtist = item.artist
//                            ) {
//
//                            }
//                        }
//                    }
                }
            }
            2 -> {
                //saved files
                Text(
                    text = "page $page",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

data class TabItems(
    val value: String,
)
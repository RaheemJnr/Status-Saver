package com.example.statussaver.ui.components

import android.widget.Toast
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.statussaver.model.Status
import com.example.statussaver.utilz.Common

@Composable
fun ImageLayout(
    status: Status,
    touchImageResource: Int,
    onSaveClicked: () -> Unit,
    onViewClicked: () -> Unit
) {
    val context = LocalContext.current
    var visible by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .size(128.dp)
            .wrapContentSize()
            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 8.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(6.dp))
            .clickable {
                visible = !visible
            }
            .border(1.3.dp, Color.Blue, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clipToBounds()
    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = status.file,
                    contentScale = ContentScale.Crop
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .alpha(0.4f)
                                    .background(Color.Black)
                            ) {
                                Image(
                                    painter = painterResource(id = touchImageResource),
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .size(30.dp)
                                        .clickable {
                                            onSaveClicked()
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Saved to ${Common.APP_DIR}",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            visible = !visible
                                        },
                                    alpha = 1f,
                                )
                                Image(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .size(30.dp)
                                        .clickable {
                                            onViewClicked()
                                            Toast
                                                .makeText(
                                                    context,
                                                    "View",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            visible = !visible
                                        },
                                    alpha = 1f,
                                )
                            }

                        }
                    }
                )
            }
        }
    }


}



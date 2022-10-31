package com.example.statussaver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.statussaver.model.Status
import java.io.File

@Composable
fun ImageLayout(
    status: Status
) {
    var editable by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
//            .size(128.dp)
//            .wrapContentSize()
//            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
//            .background(Color.Transparent, shape = RoundedCornerShape(6.dp))
//            .clickable {
//                editable = !editable
//            }
//            .border(1.3.dp, Color.Blue, RoundedCornerShape(8.dp))
//            .clip(RoundedCornerShape(8.dp))
//            .clipToBounds()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size(150.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = Icons.Default.Add,
                    contentScale = ContentScale.Crop
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Image(
                    imageVector = Icons.Default.Done,
                    contentDescription = "",
                    modifier = Modifier.size(220.dp)
                )
            }


/*
AnimatedVisibility(
visible = editable
) {

Row(
modifier = Modifier.fillMaxWidth(),
horizontalArrangement = Arrangement.Center
) {


}


}
*/
        }
    }

}


val emptyStatus = Status(
    file = File(""),
    title = "",
    path = "",
    isVideo = false,

    )

//@Preview(showBackground = true)
//@Composable
//fun ImageLayoutPreview() {
//    ImageLayout(
//        status = emptyStatus
//    )
//}


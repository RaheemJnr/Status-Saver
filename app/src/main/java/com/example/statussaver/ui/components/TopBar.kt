package com.example.statussaver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(
    title: String = "",
    titleColorFilter: Color = Color.Black,
    icon: ImageVector = Icons.Filled.ArrowBack,
    iconColorFilter: ColorFilter = ColorFilter.tint(Color.Blue),
    color: Color = MaterialTheme.colors.primary,
    startIndent: Dp = 30.dp,
    onIconClick: () -> Unit,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color),
    ) {
//

        Text(
            text = title,
            color = titleColorFilter,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 0.15.sp,
            ),
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .align(Alignment.CenterVertically)
                .padding(start = startIndent)

        )
        Spacer(modifier = Modifier.width(18.dp))
        Image(
            imageVector = icon,
            contentDescription = "Top App Bar Icon",
            colorFilter = iconColorFilter,
            modifier = Modifier
                .clickable(onClick = onIconClick)
                .padding(14.dp)
                .align(Alignment.CenterVertically)
                .size(32.dp)
        )

    }
}

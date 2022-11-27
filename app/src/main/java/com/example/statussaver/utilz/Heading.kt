package com.example.statussaver.utilz

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.statussaver.ui.theme.Dimens

@Composable
fun StatusPageHeading(
    title: String,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.h4,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colors.onBackground,
    shape: Shape = RectangleShape,
    extraItems: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding.size),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimens.SmallPadding.size),
                text = title,
                style = titleTextStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = contentColor.copy(alpha = 0.8f),
                textAlign = TextAlign.Start
            )
            extraItems()
        }
    }
}

package com.example.carscollectionsapp.presentation.customView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.example.carscollectionsapp.R

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    uri: String?,
    colorFilter: ColorFilter? = null
) {
    if (uri != null) {
        SubcomposeAsyncImage(
            model = uri,
            contentDescription = null,
            modifier = modifier
                .clickable {
                    onClick()
                }
                .fillMaxSize(),
            colorFilter = colorFilter,
            contentScale = ContentScale.Crop,
            error = {
                Surface(
                    modifier = modifier.clickable {
                        onClick()
                    }.fillMaxSize(),
                    color = Color(0xFFEEEEEE),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.image_placeholder),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        )
    } else {
        Surface(
            modifier = modifier.clickable {
                onClick()
            }.fillMaxSize(),
            color = Color(0xFFEEEEEE),
        ) {
            Icon(
                painter = painterResource(R.drawable.image_placeholder),
                contentDescription = null,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}
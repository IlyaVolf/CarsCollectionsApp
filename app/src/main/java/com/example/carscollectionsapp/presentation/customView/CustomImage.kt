package com.example.carscollectionsapp.presentation.customView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
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
    onClick: (() -> Unit)? = null,
    uri: String?,
    colorFilter: ColorFilter? = null
) {
    if (!uri.isNullOrBlank()) {
        SubcomposeAsyncImage(
            model = uri,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize(),
            colorFilter = colorFilter,
            contentScale = ContentScale.Crop,
            error = {
                Surface(
                    modifier = modifier
                        .fillMaxSize(),
                    color = Color(0xFFEEEEEE),
                ) {
                    Image(
                        painter = painterResource(R.drawable.image_placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        )
    } else {
        Surface(
            modifier = modifier
                .fillMaxSize(),
            color = Color(0xFFEEEEEE),
        ) {
            Image(
                painter = painterResource(R.drawable.image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}
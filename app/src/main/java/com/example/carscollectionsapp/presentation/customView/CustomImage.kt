package com.example.carscollectionsapp.presentation.customView

/*@Composable
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
            contentScale = ContentScale.Crop
        )
    } else {
        Surface(
            modifier = modifier.clickable {
                onClick()
            }.fillMaxSize(),
            color = Color(0xFFEEEEEE),
        ) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = null,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}*/
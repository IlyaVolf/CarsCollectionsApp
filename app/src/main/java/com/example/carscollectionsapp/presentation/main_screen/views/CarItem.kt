package com.example.carscollectionsapp.presentation.main_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.domain.entities.Car
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CarItem(
    car: Car,
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .clickable {
                onCarClicked(car.id)
            },
        shape = RoundedCornerShape(16.dp),
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                imageModel = { R.drawable.image_placeholder },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(180.dp),
                previewPlaceholder = R.drawable.image_placeholder,

            )

            Text(
                text = car.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                maxLines = 1,
            )

        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    CarItem(car = Car(0, "abcdefghjklmnopqrstuvwnz123456789", "https://www.protoolreviews.com/wp-content/uploads/2017/08/Hilti-SF-6H-A22-22V-Hammer-Drill-01-650x434.jpg", 1999, 1.4F, 11L))
}
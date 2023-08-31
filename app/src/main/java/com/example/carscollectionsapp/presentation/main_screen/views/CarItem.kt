package com.example.carscollectionsapp.presentation.main_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.customView.CustomImage

@Composable
fun CarItem(
    car: Car,
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Box(
            modifier = Modifier
            .clickable {
            onCarClicked(car.id)
        },
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomImage(
                    uri = car.photo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.33f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(24.dp)),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = car.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    CarItem(car = Car(0, "abcdefghjklmnopqrstuvwnz1fsssssssssssssssssssssssssss23456789", "", 1999, 1.4F, 11L))
}
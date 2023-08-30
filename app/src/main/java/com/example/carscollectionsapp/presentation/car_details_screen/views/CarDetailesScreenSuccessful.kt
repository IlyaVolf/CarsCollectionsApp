package com.example.carscollectionsapp.presentation.car_details_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.domain.entities.Car
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CarDetailsScreenSuccessful(
    car: Car,
    modifier: Modifier = Modifier,
    onCarClicked: (Long) -> Unit = {},
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
        ) {

            GlideImage(
                imageModel = { R.drawable.car },
                modifier = Modifier
                    .fillMaxWidth(),
                previewPlaceholder = R.drawable.car,
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Text(
                        text = car.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Start
                    )
                }

                item {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = stringResource(R.string.production_year),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = car.year.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                item {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = stringResource(R.string.engine_capacity),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = car.engineCapacity.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                    }
                }

            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun test() {
    CarDetailsScreenSuccessful(
        Car(1, "Lada Vesta", null, 2019, 1.6F, 0L)
    )
}

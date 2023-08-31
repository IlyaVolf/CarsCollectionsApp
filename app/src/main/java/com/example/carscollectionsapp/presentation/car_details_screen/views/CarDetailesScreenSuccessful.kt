package com.example.carscollectionsapp.presentation.car_details_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.domain.entities.Car
import com.example.carscollectionsapp.presentation.customView.CustomImage

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
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CustomImage(
                uri = car.photo,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                Text(
                    text = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )


                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(8.dp)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(80.dp)
                    ) {
                        Text(
                            text = car.year.toString(),
                            fontSize = 21.sp,
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(R.string.production_year),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )

                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(80.dp)
                    ) {
                        Text(
                            fontSize = 21.sp,
                            text = car.engineCapacity.toString(),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(R.string.engine_capacity),
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
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

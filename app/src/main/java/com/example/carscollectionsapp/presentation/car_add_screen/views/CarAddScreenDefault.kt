package com.example.carscollectionsapp.presentation.car_add_screen.views

import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddContainer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.carscollectionsapp.presentation.car_add_screen.entities.CarAddScreenEvent
import com.example.carscollectionsapp.presentation.customView.CustomButton
import com.example.carscollectionsapp.presentation.customView.CustomImage
import com.example.carscollectionsapp.presentation.customView.CustomTextField

@Composable
fun CarAddScreenDefault(
    carAddContainer: CarAddContainer,
    modifier: Modifier = Modifier,
    onAction: (CarAddScreenEvent) -> Unit,
) {

    val scrollState = rememberScrollState()
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) {
        onAction(CarAddScreenEvent.OnPhotoChanged(it.toString()))
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f)
        ) {
            CustomImage(
                uri = carAddContainer.photoString,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(54.dp))
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = PickVisualMedia.ImageOnly
                            )
                        )
                    },
            )
            CustomTextField(
                label = stringResource(R.string.car_name),
                isNecessaryField = true,
                value = carAddContainer.nameString,
                singleLine = true,
                onValueChange = { onAction(CarAddScreenEvent.OnNameChanged(it)) },
                state = carAddContainer.nameState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                imeAction = ImeAction.Next
            )
            CustomTextField(
                label = stringResource(R.string.production_year),
                isNecessaryField = true,
                value = carAddContainer.yearString,
                singleLine = true,
                onValueChange = { onAction(CarAddScreenEvent.OnYearChanged(it)) },
                keyboardType = KeyboardType.Number,
                state = carAddContainer.yearState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                imeAction = ImeAction.Next
            )
            CustomTextField(
                label = stringResource(R.string.engine_capacity),
                isNecessaryField = true,
                value = carAddContainer.engineCapacityString,
                singleLine = true,
                onValueChange = { onAction(CarAddScreenEvent.OnEngineCapacityChanged(it)) },
                keyboardType = KeyboardType.Decimal,
                state = carAddContainer.engineCapacityState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                imeAction = ImeAction.Go
            )

            Spacer(modifier = Modifier.height(16.dp))

        }

        CustomButton(
            text = stringResource(R.string.save),
            onClick = {
                onAction(CarAddScreenEvent.OnSaveClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        CustomButton(
            text = stringResource(R.string.cancel),
            outlined = true,
            onClick = { onAction(CarAddScreenEvent.OnCancelClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun test() {
    /*CarAddScreenSuccessful(
        Car(1, "Lada Vesta", null, 2019, 1.6F, 0L)
    )*/
}
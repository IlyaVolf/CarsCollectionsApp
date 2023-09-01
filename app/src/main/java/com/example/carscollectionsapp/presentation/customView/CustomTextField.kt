package com.example.carscollectionsapp.presentation.customView

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.carscollectionsapp.R
import com.example.carscollectionsapp.presentation.entities.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    minHeight: Dp = Dp.Unspecified,
    label: String,
    value: String,
    singleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
    state: TextFieldState,
    imeAction: ImeAction = ImeAction.Go,
    isNecessaryField: Boolean
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label
            )
        },
        singleLine = singleLine,
        isError = (state != TextFieldState.OK && state != TextFieldState.INIT),
        supportingText = {
            when (state) {
                TextFieldState.INIT -> {
                    if (isNecessaryField) {
                        Text(text = stringResource(R.string.necessary_field))
                    } else {
                        Text(text = stringResource(R.string.optional_field))
                    }
                }

                TextFieldState.OK -> {}
                TextFieldState.EMPTY -> {
                    if (isNecessaryField) {
                        Text(text = stringResource(R.string.necessary_field))
                    } else {
                        Text(text = stringResource(R.string.optional_field))
                    }
                }

                TextFieldState.INVALID -> {
                    Text(text = stringResource(R.string.invalid_value))
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        shape = RoundedCornerShape(32.dp)
    )
}

@Preview
@Composable
fun Test() {
    CustomTextField(
        label = "label",
        value = "value",
        onValueChange = {},
        state = TextFieldState.OK,
        isNecessaryField = true,
        singleLine = true
    )
}
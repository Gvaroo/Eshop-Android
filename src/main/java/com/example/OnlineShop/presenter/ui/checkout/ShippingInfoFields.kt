package com.example.OnlineShop.presenter.ui.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.OnlineShop.presenter.dto.Checkout.ShippingInfoDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingInfoFields(
    shippingInfo: ShippingInfoDTO,
    onShippingInfoChange: (ShippingInfoDTO) -> Unit
) {
    Column {
        OutlinedTextField(
            value = shippingInfo.shippingAddress ?: "",
            onValueChange = {
                onShippingInfoChange(shippingInfo.copy(shippingAddress = it))
            },
            label = { Text("Shipping Address") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = shippingInfo.shippingCountry ?: "",
            onValueChange = {
                onShippingInfoChange(shippingInfo.copy(shippingCountry = it))
            },
            label = { Text("Shipping Country") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = shippingInfo.shippingCity ?: "",
            onValueChange = {
                onShippingInfoChange(shippingInfo.copy(shippingCity = it))
            },
            label = { Text("Shipping City") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = shippingInfo.zipCode ?: "",
            onValueChange = {
                onShippingInfoChange(shippingInfo.copy(zipCode = it))
            },
            label = { Text("Zip Code") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = shippingInfo.phoneNumber?.toString() ?: "",
            onValueChange = {
                onShippingInfoChange(shippingInfo.copy(phoneNumber = it.toIntOrNull() ?: 0))
            },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )

    }

}
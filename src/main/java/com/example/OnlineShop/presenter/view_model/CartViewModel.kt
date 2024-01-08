package com.example.OnlineShop.presenter.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.OnlineShop.domain.use_cases.Cart.CartUseCases
import com.example.OnlineShop.presenter.dto.Cart.CartDataDTO
import com.example.OnlineShop.presenter.dto.Cart.CartProductDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCases
) : ViewModel() {
    private val _Message = mutableStateOf<String?>(null)
    val Message: State<String?> get() = _Message

    private val _cartData = MutableStateFlow<List<CartProductDTO>>(emptyList())
    val cartData: StateFlow<List<CartProductDTO>> = _cartData


    fun updateCartItem(itemId: Int, newQuantity: Int) {
        val updatedCartData = _cartData.value?.map { cartItem ->
            if (cartItem.productId == itemId) {
                cartItem.copy(quantity = newQuantity)
            } else {
                cartItem
            }
        }
        if (updatedCartData != null) {
            _cartData.value = updatedCartData
        }

        val cartProductData: MutableList<CartDataDTO> = mutableListOf()

        _cartData.value.mapTo(cartProductData) { cartItem ->
            CartDataDTO(cartItem.productId, cartItem.quantity)
        }

        viewModelScope.launch {
            AddOrUpdateProductsToCart(cartProductData)
        }
    }
    suspend fun AddOrUpdateProductsToCart(cartData:MutableList<CartDataDTO>)  {
        val response = useCase.addOrUpdateProductsToCartUseCase.execute(cartData)
        if(response.isSuccessful){
            response.body()?.data?.let { showSnackbar(it) }
        } else {
            val errorResponse = response.errorBody()?.string()

            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            showSnackbar(errorMessage)
        }
    }
    suspend fun GetUserCartData() {
        val response = useCase.getUserCartDataUseCase.execute()
        if(response.isSuccessful) {
            var cartData =  response.body()?.data
            _cartData.value = cartData ?: emptyList()
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            showSnackbar(errorMessage)
        }

    }
    suspend fun DeleteProductFromCart(productId: Int) {
        val response = useCase.deleteProductFromCartUseCase.execute(productId)

        if (response.isSuccessful) {
            response.body()?.data?.let { showSnackbar(it) }
            viewModelScope.launch {
                GetUserCartData()
            }
        } else {
            val errorResponse = response.errorBody()?.string()

            if (errorResponse != null && errorResponse.isNotEmpty()) {
                try {
                    val jsonObject = JSONObject(errorResponse)
                    val errorMessage = jsonObject.getString("message")
                    showSnackbar(errorMessage)
                } catch (e: JSONException) {
                    // Handle JSON parsing error
                    e.printStackTrace()
                    showSnackbar("Error parsing JSON response")
                }
            } else {
                // Handle the case where errorResponse is null or empty
                showSnackbar("Unknown error occurred")
            }
        }
    }

    private fun showSnackbar(message: String) {
        _Message.value = message
    }
}
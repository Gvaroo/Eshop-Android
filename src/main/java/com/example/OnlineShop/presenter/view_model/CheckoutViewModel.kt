package com.example.OnlineShop.presenter.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.OnlineShop.domain.use_cases.Checkout.CheckoutUseCases
import com.example.OnlineShop.presenter.dto.Checkout.CheckoutDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderDTO
import com.example.OnlineShop.presenter.dto.Checkout.GetOrderHistoryDTO
import com.example.OnlineShop.presenter.dto.Checkout.ShippingInfoDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val useCase: CheckoutUseCases
) : ViewModel() {
    private val _Message = MutableStateFlow<String?>(null)
    val Message: StateFlow<String?> get() = _Message
    private val _shippingInfo = MutableStateFlow<ShippingInfoDTO>(ShippingInfoDTO())
    val shippingInfo: StateFlow<ShippingInfoDTO> = _shippingInfo
    private val _userOrderedProducts = MutableStateFlow<GetOrderDTO?>(null)
    val userOrderedProducts: StateFlow<GetOrderDTO?> = _userOrderedProducts

    suspend fun getShippingInfo() {
        val response = useCase.getShippingInfoUseCase.execute()
        if(response.isSuccessful){
            var info = response.body()?.data
            _shippingInfo.value = info ?: ShippingInfoDTO()
        } else{
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            Log.d("!!!!!!!","Error message: $errorMessage")
        }
    }
    suspend fun checkout(navController: NavController) {
        val data = CheckoutDTO(shippingInfo.value)
        val response = useCase.orderUseCase.execute(data)
        if(response.isSuccessful){
             showSnackbar("Checkout successful. Thank you for your purchase!")
             delay(3000)
             navController.navigate("mainScreen")
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            showSnackbar(errorMessage)
            delay(3000)
            navController.navigate("mainScreen")
        }
    }

    suspend fun getUserOrderHistory():List<GetOrderHistoryDTO>? {
        val response = useCase.getUserOrderHistoryUseCase.execute()
        if(response.isSuccessful){
            return response.body()?.data
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            showSnackbar(errorMessage)
        }
        return null
    }
    suspend fun getUserOrderedProducts(orderId:Int) {
       val response = useCase.getUserOrderedProducts.execute(orderId)
        if(response.isSuccessful) {
            _userOrderedProducts.value = response.body()?.data
        }else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            showSnackbar(errorMessage)
        }

    }
    fun updateShippingInfo(newShippingInfo: ShippingInfoDTO) {
        _shippingInfo.value = newShippingInfo
    }
    private fun showSnackbar(message: String) {
        _Message.value = message
    }
}
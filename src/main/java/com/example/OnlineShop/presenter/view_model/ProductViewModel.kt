package com.example.OnlineShop.presenter.view_model

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.OnlineShop.domain.use_cases.Product.ProductUseCases
import com.example.OnlineShop.presenter.dto.Product.AddProductReviewDTO
import com.example.OnlineShop.presenter.dto.Product.CategoriesDTO
import com.example.OnlineShop.presenter.dto.Product.ProductDTO
import com.example.OnlineShop.presenter.dto.Product.ProductReviewsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCase: ProductUseCases
) : ViewModel()  {
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage
    private val _categorySelected = MutableStateFlow<String?>(null)
    val categorySelected: StateFlow<String?> get() = _categorySelected
    private val _searchText = MutableStateFlow<String?>(null)
    val searchText: StateFlow<String?> get() = _searchText
    private val _productReviewsState = MutableStateFlow<List<ProductReviewsDTO>>(emptyList())
    val productReviewsState: StateFlow<List<ProductReviewsDTO>> = _productReviewsState




    suspend fun getCategories() : List<CategoriesDTO>? {
        val response = useCase.getCategoriesUseCase.execute()
        if(response.isSuccessful) {
            Log.d("!!!!!!!!", response.body()?.data.toString())
            return response.body()?.data
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            Log.d("!!!!!!", "Error Message: $errorMessage")
        }
        return null
    }

    suspend fun getProducts(): List<ProductDTO>? {
        val response = useCase.getProductsUseCase.execute()
        if(response.isSuccessful) {
            Log.d("!!!!!!!!", response.body()?.data.toString())
            return response.body()?.data
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            Log.d("!!!!!!", "Error Message: $errorMessage")
        }
        return null
    }


    @SuppressLint("SuspiciousIndentation")
    suspend fun getProductById(id:Int): ProductDTO? {

        val response =  useCase.getProductUseCase.execute(id)
            if(response.isSuccessful) {
                Log.d("!!!!!!!!", response.body()?.data.toString())
                return response.body()?.data
            } else {
                val errorResponse = response.errorBody()?.string()
                val jsonObject = JSONObject(errorResponse)
                val errorMessage = jsonObject.getString("message")
                Log.d("!!!!!!", "Error Message: $errorMessage")
            }

        return null
    }

suspend fun getProductReviews(productId: Int) {
    try {
        val response = useCase.getProductReviewsUseCase.execute(productId)
        if (response.isSuccessful) {
            val productReviews = response.body()?.data?.productReviews
            Log.d("!!!!!!", productReviews.toString())
            _productReviewsState.value = productReviews ?: emptyList()
        } else {
            _productReviewsState.value = emptyList()
        }
    } catch (e: Exception) {
        _productReviewsState.value = emptyList()
    }
}

    suspend fun addProductReviews(review: AddProductReviewDTO) {
        Log.d("!!!!!!", "ARE WE HERE?")
        val response = useCase.addProductReviewUseCase.execute(review)

        if (response.isSuccessful) {
            Log.d("!!!!!!", "ARE WE HERE?")
            getProductReviews(review.productId)
        } else {
            val errorResponse = response.errorBody()?.string()

            if (errorResponse != null) {
                try {
                    val jsonObject = JSONObject(errorResponse)

                    if (jsonObject.has("message")) {
                        val errorMessage = jsonObject.getString("message")
                        showSnackbar(errorMessage)
                        Log.d("!!!!!!", "Error Message: $errorMessage")
                        _errorMessage.value = errorMessage
                    } else {
                        Log.d("!!!!!!", "Error Message: Unknown error")
                        _errorMessage.value = "Message should be atleast 10 character long."
                    }
                } catch (e: JSONException) {
                    Log.e("!!!!!!", "Error parsing JSON: ${e.message}")
                    _errorMessage.value = "Error parsing JSON"
                }
            } else {
                Log.e("!!!!!!", "Error Body is null")
                _errorMessage.value = "Error Body is null"
            }
        }
    }


    fun selectCategory(category: String) {
        _categorySelected.value = category

    }
    fun searchProduct(text:String) {
        _searchText.value = text
    }
     fun showSnackbar(message: String) {
        _errorMessage.value = message
    }
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

}
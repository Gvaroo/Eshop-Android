package com.example.retrofit.presenter.view_model

import android.content.SharedPreferences
import android.util.Log.d
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.OnlineShop.domain.use_cases.Auth.UseCases
import com.example.OnlineShop.presenter.dto.Auth.UserDTO
import com.example.OnlineShop.presenter.dto.Auth.loginDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: UseCases,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage
    private val DARK_MODE_KEY = "dark_mode_key"

    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> get() = _isDarkMode

    init {
        _isDarkMode.value = sharedPreferences.getBoolean(DARK_MODE_KEY, false)
    }


    fun toggleDarkMode() {
        _isDarkMode.value = _isDarkMode.value?.not() ?: false
        saveDarkModePreference(_isDarkMode.value ?: false)
    }


    suspend fun Register(user: UserDTO, navController: NavController) {
        val response = useCase.registerByUseCase.execute(user)
        if (response.isSuccessful) {
            showSnackbar("You are registered. Please login!")
            delay(3000)
            navController.navigate("loginScreen")
        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            d("!!!!!!", "Error Message: $errorMessage")
            _errorMessage.value = errorMessage
        }
    }

    suspend fun Login(user: loginDTO, navController: NavController) {

        try {
            val response = useCase.LoginUseCase.execute(user)

            if (response.isSuccessful) {
                val editor = sharedPreferences.edit()
                editor.putString("fullname",  response.body()?.data?.fullName)
                editor.putString("email",  response.body()?.data?.email)
                editor.apply()
                navController.navigate("mainScreen")
            } else {
                val errorResponse = response.errorBody()?.string()
                val jsonObject = JSONObject(errorResponse)
                val errorMessage = jsonObject.getString("message")
                d("!!!!!!", "Error Message: $errorMessage")
                _errorMessage.value = errorMessage


            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
    suspend fun isLoggedIn(): Boolean {
        val response = useCase.IsLoggedInUseCase.execute()
        return if (response.isSuccessful) {
            d("!!!!!!", response.body()?.data.toString())
            response.body()?.data == true

        } else {
            false
        }
    }
    suspend fun LogOut(navController: NavController) {
        val response = useCase.LogOutUseCase.execute()
        if (response.isSuccessful) {
            val editor = sharedPreferences.edit()
            editor.remove("access_token")
            editor.apply()
            navController.navigate("loginScreen")

        } else {
            val errorResponse = response.errorBody()?.string()
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.getString("message")
            d("!!!!!!", "Error Message: $errorMessage")
            _errorMessage.value = errorMessage
        }
    }
    private fun saveDarkModePreference(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(DARK_MODE_KEY, isDarkMode).apply()
    }
    fun checkIfItsValid(email: String, password: String, fullName: String, confirmPassword: String): Boolean {
        return when {
            !email.endsWith("@gmail.com") -> {
                showSnackbar("Email must end with @gmail.com")
                false
            }
            password.length < 8 -> {
                showSnackbar("Password must have at least 8 characters")
                false
            }
            !password.any { it.isUpperCase() } -> {
                showSnackbar("Password must have at least 1 uppercase letter")
                false
            }
            !password.any { it.isLowerCase() } -> {
                showSnackbar("Password must have at least 1 lowercase letter")
                false
            }
            !password.any { it.isDigit() } -> {
                showSnackbar("Password must have at least 1 digit")
                false
            }
            fullName.isEmpty() -> {
                showSnackbar("Full Name cannot be empty")
                false
            }
            password != confirmPassword -> {
                showSnackbar("Passwords do not match")
                false
            }
            else -> {
                true
            }
        }
    }
    fun getUserInformation(): MutableList<String> {
        val storedFullname = sharedPreferences.getString("fullname", "")
        val storedEmail = sharedPreferences.getString("email", "")

        val userInfo = mutableListOf<String>()

        if (storedFullname != null) {
            userInfo.add(storedFullname)

            if (storedEmail != null) {
                userInfo.add(storedEmail)
            }
        }
        return userInfo
    }


    private fun showSnackbar(message: String) {
        _errorMessage.value = message
    }
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
 }
package com.example.OnlineShop

import com.example.OnlineShop.presenter.ui.screens.MainScreen
import com.example.OnlineShop.presenter.ui.screens.RegisterScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.OnlineShop.presenter.ui.screens.CartScreen
import com.example.OnlineShop.presenter.ui.screens.CheckoutScreen
import com.example.OnlineShop.presenter.ui.screens.DetailProductScreen
import com.example.OnlineShop.presenter.ui.screens.DetailTransactionScreen
import com.example.OnlineShop.presenter.ui.screens.LoginScreen
import com.example.OnlineShop.presenter.ui.screens.ProfileScreen
import com.example.OnlineShop.presenter.ui.screens.SettingsScreen
import com.example.OnlineShop.presenter.ui.screens.SplashScreen
import com.example.OnlineShop.ui.theme.ComposetestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposetestTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splashScreen") {
                    composable("splashScreen") {
                        SplashScreen(
                            navigateToNextScreen = {
                                navController.navigate("mainScreen")
                            }
                        )
                    }
                    composable("mainScreen") {
                        MainScreen(navController)
                    }
                    composable("loginScreen") {
                        LoginScreen(navController)
                    }
                    composable("registerScreen") {
                        RegisterScreen(navController)
                    }
                    composable("cartScreen") {
                        CartScreen(navController)
                    }
                    composable("profileScreen") {
                        ProfileScreen(navController)
                    }
                    composable("settingsScreen") {
                        SettingsScreen(navController)
                    }
                    composable(
                        route = "detailProductScreen/{productId}",
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId")
                        if (productId != null) {
                            DetailProductScreen(navController, productId)
                        }
                    }
                    composable(
                        route = "checkoutScreen/{totalPrice}",
                        arguments = listOf(navArgument("totalPrice") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val totalPrice = backStackEntry.arguments?.getInt("totalPrice")
                        if (totalPrice != null) {
                            CheckoutScreen(navController, totalPrice)
                        }
                    }
                    composable(
                        route = "detailTransactionScreen/{orderId}",
                        arguments = listOf(navArgument("orderId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val orderId = backStackEntry.arguments?.getInt("orderId")
                        if (orderId != null) {
                            DetailTransactionScreen(navController, orderId)
                        }
                    }
                }
            }
        }
    }


}


package com.mkavaktech.readingtrackers.core.init.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.view.auth.login.LoginView
import com.mkavaktech.readingtrackers.view.auth.splash.SplashView
import com.mkavaktech.readingtrackers.view.main.details.DetailsView
import com.mkavaktech.readingtrackers.view.main.home.HomeView
import com.mkavaktech.readingtrackers.view.main.home.viewmodel.HomeViewModel
import com.mkavaktech.readingtrackers.view.main.search.SearchView
import com.mkavaktech.readingtrackers.view.main.stats.StatsView
import com.mkavaktech.readingtrackers.view.main.update.UpdateView

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun NavigationService() {
    val navController = rememberNavController()
    val navHost =
        NavHost(
            navController = navController, startDestination = NavigationEnums.SplashView.name
        ) {
            composable(route = NavigationEnums.SplashView.name) {
                SplashView(navController = navController)
            }
            composable(route = NavigationEnums.LoginView.name) {
                LoginView(navController = navController)
            }
            composable(route = NavigationEnums.HomeView.name) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeView(navController = navController, viewModel = homeViewModel)
            }
            composable(route = NavigationEnums.StatsView.name) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                StatsView(navController = navController, viewModel = homeViewModel)
            }
            composable(route = NavigationEnums.SearchView.name) {
                SearchView(navController = navController, viewModel = hiltViewModel())
            }

            val detailsRoute = NavigationEnums.DetailsView.name
            composable(
                route = "$detailsRoute/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.StringType })
            ) { navBackStack ->
                navBackStack.arguments?.getString("bookId").let {
                    DetailsView(navController = navController, bookId = it!!)
                }
            }

            val updateRoute = NavigationEnums.UpdateView.name
            composable(
                "$updateRoute/{bookItemId}",
                arguments = listOf(navArgument("bookItemId") { type = NavType.StringType })
            ) { navBackStack ->
                navBackStack.arguments?.getString("bookItemId")
                    .let { UpdateView(navController = navController, bookItemId = it!!) }
            }
        }
}
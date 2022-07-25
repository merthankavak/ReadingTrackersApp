package com.mkavaktech.readingtrackers.core.constants.enums

enum class NavigationEnums {
    SplashView,
    LoginView,
    HomeView,
    StatsView,
    SearchView,
    UpdateView,
    DetailsView;

    companion object {
        fun fromRoute(route: String?): NavigationEnums =
            when (route?.substringBefore("/")) {
                SplashView.name -> SplashView
                LoginView.name -> LoginView
                HomeView.name -> HomeView
                StatsView.name -> StatsView
                SearchView.name -> SearchView
                UpdateView.name -> UpdateView
                DetailsView.name -> DetailsView
                null -> HomeView
                else -> throw IllegalArgumentException("Route $route is not found!")
            }
    }
}
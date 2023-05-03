package com.example.transportsystem

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen")
    object First: Screen(route = "first_screen")
    object Map: Screen(route = "map_screen")
}

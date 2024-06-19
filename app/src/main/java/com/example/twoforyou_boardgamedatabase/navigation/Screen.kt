package com.example.twoforyou_boardgamedatabase.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {

    @Serializable
    object DisplayScreen

    @Serializable
    data class DetailScreen(
        val id: Int = 0
    )
}

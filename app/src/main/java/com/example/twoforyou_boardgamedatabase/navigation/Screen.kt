package com.example.twoforyou_boardgamedatabase.navigation

import kotlinx.serialization.Serializable

sealed class Screen() {

    @Serializable
    object DisplayScreen

    @Serializable
    object DetailScreen {
        val boardgameId: Int = 0
    }
}

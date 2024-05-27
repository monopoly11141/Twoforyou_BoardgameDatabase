package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ratings")
data class Ratings(
    @Element(name="ranks")
    val ranks: Ranks
)

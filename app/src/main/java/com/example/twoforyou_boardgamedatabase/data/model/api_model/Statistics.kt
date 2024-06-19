package com.example.twoforyou_boardgamedatabase.data.model.api_model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "statistics")
data class Statistics(
    @Element(name="ratings")
    val ratings: Ratings = Ratings(),
)

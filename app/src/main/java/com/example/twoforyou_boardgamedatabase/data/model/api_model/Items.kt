package com.example.twoforyou_boardgamedatabase.data.model.api_model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
@Xml(name= "items")
data class Items(
    @Element(name="item")
    val item: Item = Item(),
)
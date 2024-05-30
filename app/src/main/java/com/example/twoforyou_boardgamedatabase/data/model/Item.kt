package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class Item(
    @PropertyElement(name = "description")
    var description: String = "",
    @Element
    var link: List<Link>,
    @Element(name = "statistics")
    var statistics: Statistics
)


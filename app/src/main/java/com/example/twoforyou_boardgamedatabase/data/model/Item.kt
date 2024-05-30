package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class Item(
    @Element(name = "name")
    var name: List<Name> = listOf(),
    @PropertyElement(name = "description")
    var description: String = "",
    @Element(name = "link")
    var link: List<Link> = listOf(),
    @Element(name = "statistics")
    var statistics: Statistics = Statistics()
)


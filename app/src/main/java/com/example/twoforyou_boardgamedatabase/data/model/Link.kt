package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "link")
data class Link(
    @Attribute(name = "type")
    var type: String = "",
    @Attribute(name = "value")
    var value: String = ""
)

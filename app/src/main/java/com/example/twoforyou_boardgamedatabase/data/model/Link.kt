package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "link")
data class Link(
    @Attribute(name = "value") // name is optional, per default the field name will be used as name
    var value: String = ""
)

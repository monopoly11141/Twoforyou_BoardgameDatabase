package com.example.twoforyou_boardgamedatabase.data.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "ranks")
data class Ranks(
    @Path("rank")
    @Attribute(name = "bayesaverage")
    val bayesaverage: String
)

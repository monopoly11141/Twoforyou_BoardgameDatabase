package com.example.twoforyou_boardgamedatabase.ui.display.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoardgameMechanismItem(
    mechanism: String
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(vertical = 2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {
        Text(
            text = mechanism,
            fontSize = 12.sp
        )
    }
}
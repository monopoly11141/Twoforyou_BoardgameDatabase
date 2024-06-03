package com.example.twoforyou_boardgamedatabase.ui.display.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayViewModel

@Composable
fun Boardgame(
    boardgameItem: BoardgameItem,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    Column(

    ) {
        Text(
            text = viewModel.pickName(boardgameItem.item.name),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = boardgameItem.item.imageUrl,
                contentDescription = "보드게임 이미지",
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
            )

            IconButton(
                onClick = { viewModel.deleteBoardgameItem(boardgameItem)  },
                modifier = Modifier
                    .align(Alignment.Bottom)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "삭제하기")
            }
        }

    }


}
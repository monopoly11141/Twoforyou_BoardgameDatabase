package com.example.twoforyou_boardgamedatabase.ui.display.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayViewModel

@Composable
fun Boardgame(
    boardgameItem: BoardgameItem,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    var showDeleteBoardgameDialog by remember { mutableStateOf(false) }
    var showEditBoardgameDialog by remember { mutableStateOf(false) }
    var editBoardgameDialogKoreanName by remember { mutableStateOf(boardgameItem.koreanName) }
    Column(

    ) {
        Text(
            text = boardgameItem.koreanName.ifBlank { boardgameItem.englishName },
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            AsyncImage(
                model = boardgameItem.imageUrl,
                contentDescription = "보드게임 이미지",
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(
                    onClick = { showEditBoardgameDialog = true },
                    modifier = Modifier
                        .align(Alignment.Bottom)
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "수정하기")
                }

                IconButton(
                    onClick = { showDeleteBoardgameDialog = true },
                    modifier = Modifier
                        .align(Alignment.Bottom)
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "삭제하기")
                }
            }

        }

    }

    if (showEditBoardgameDialog) {
        editBoardgameDialogKoreanName = boardgameItem.koreanName
        Dialog(onDismissRequest = { showEditBoardgameDialog = false }) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "한국어 제목 추가/수정",
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                TextField(
                    value = editBoardgameDialogKoreanName,
                    onValueChange = { newKoreanName ->
                        editBoardgameDialogKoreanName = newKoreanName
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showEditBoardgameDialog = false
                        },
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Text(text = "취소")
                    }
                    Button(
                        onClick = {
                            boardgameItem.koreanName = editBoardgameDialogKoreanName
                            viewModel.editBoardgameItem(boardgameItem)
                            editBoardgameDialogKoreanName = ""
                            showEditBoardgameDialog = false
                        },
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Text(text = "수정")
                    }
                }
            }
        }
    }

    if (showDeleteBoardgameDialog) {
        Dialog(onDismissRequest = { showDeleteBoardgameDialog = false }) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${boardgameItem.koreanName.ifBlank { boardgameItem.englishName }}을(를) 삭제하나요?",
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showDeleteBoardgameDialog = false
                        },
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Text(text = "아니요.")
                    }
                    Button(
                        onClick = {
                            viewModel.deleteBoardgameItem(boardgameItem)
                            showDeleteBoardgameDialog = false
                        },
                        modifier = Modifier
                            .weight(0.5f)
                    ) {
                        Text(text = "네.")
                    }
                }

            }

        }
    }


}



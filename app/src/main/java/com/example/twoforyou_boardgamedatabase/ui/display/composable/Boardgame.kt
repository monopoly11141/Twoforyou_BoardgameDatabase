package com.example.twoforyou_boardgamedatabase.ui.display.composable

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.twoforyou_boardgamedatabase.data.model.BoardgameItem
import com.example.twoforyou_boardgamedatabase.navigation.Screen
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayViewModel
import java.math.RoundingMode

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Boardgame(
    boardgameItem: BoardgameItem,
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    var showDeleteBoardgameDialog by remember { mutableStateOf(false) }
    var showEditBoardgameDialog by remember { mutableStateOf(false) }
    var isFavoriteClicked by remember { mutableStateOf(false) }
    var editBoardgameDialogKoreanName by remember { mutableStateOf(boardgameItem.koreanName) }
    var favoriteImageVector by remember { mutableStateOf(getFavoriteImageVector(boardgameItem)) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(boardgameItem.imageUrl).build(),
        contentScale = ContentScale.Fit
    )

    val df = DecimalFormat("0.00")
    df.roundingMode = RoundingMode.HALF_UP.ordinal

    Column(
        modifier = Modifier
            .paint(
                painter,
                contentScale = ContentScale.Fit,
                alpha = 0.2f,
                alignment = Alignment.CenterEnd
            )
            .clickable {
                navController.navigate(
                    Screen.DetailScreen(boardgameItem.id)
                )
            }
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = boardgameItem.koreanName.ifBlank { boardgameItem.englishName },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            AsyncImage(
                model = boardgameItem.imageUrl,
                contentDescription = "보드게임 이미지",
                modifier = Modifier
                    .size(120.dp)
                    .padding(5.dp)
            )

            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "긱 순위 : ${boardgameItem.ranking} 위"
                )

                Text(
                    text = if (boardgameItem.minPlayersValue == boardgameItem.maxPlayersValue) "인원 : ${boardgameItem.minPlayersValue} 명"
                    else "인원 : ${boardgameItem.minPlayersValue}~${boardgameItem.maxPlayersValue} 명"
                )

                Text(
                    text = if (boardgameItem.minPlayTimeValue == boardgameItem.maxPlayTimeValue) "시간 : ${boardgameItem.maxPlayTimeValue} 분"
                    else "시간 : ${boardgameItem.minPlayTimeValue}~${boardgameItem.maxPlayTimeValue} 분"
                )

                Text(
                    text = "복잡도 : ${df.format(boardgameItem.averageWeight)}"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    modifier = Modifier
                        .drawBehind {
                            drawCircle(
                                color = Color.Gray,
                                radius = this.size.maxDimension / 2.0f
                            )
                        }
                        .padding(horizontal = 16.dp)
                    ,
                    text = df.format(boardgameItem.bayesAverageValue),
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {

            IconButton(
                onClick = { isFavoriteClicked = !isFavoriteClicked },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(imageVector = favoriteImageVector, contentDescription = "즐겨찾기")
            }

            IconButton(
                onClick = { showEditBoardgameDialog = true },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "수정하기")
            }

            IconButton(
                onClick = { showDeleteBoardgameDialog = true },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "삭제하기")
            }
        }


        FlowRow(
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            boardgameItem.linkValueList.forEach { mechanism ->
                BoardgameMechanismItem(mechanism)
                Spacer(
                    modifier = Modifier.width(2.dp)
                )
            }
        }
    }

    if (isFavoriteClicked) {
        boardgameItem.isFavorite = !boardgameItem.isFavorite
        favoriteImageVector = getFavoriteImageVector(boardgameItem)
        viewModel.editBoardgameItem(boardgameItem)
        isFavoriteClicked = false
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
                    text = "한국어 제목 추가/수정", modifier = Modifier.padding(vertical = 4.dp)
                )
                TextField(value = editBoardgameDialogKoreanName, onValueChange = { newKoreanName ->
                    editBoardgameDialogKoreanName = newKoreanName
                })
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showEditBoardgameDialog = false
                        }, modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text = "취소")
                    }
                    Button(
                        onClick = {
                            boardgameItem.koreanName = editBoardgameDialogKoreanName
                            viewModel.editBoardgameItem(boardgameItem)
                            editBoardgameDialogKoreanName = ""
                            showEditBoardgameDialog = false
                        }, modifier = Modifier.weight(0.5f)
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
                        }, modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text = "아니요.")
                    }
                    Button(
                        onClick = {
                            viewModel.deleteBoardgameItem(boardgameItem)
                            showDeleteBoardgameDialog = false
                        }, modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text = "네.")
                    }
                }

            }

        }
    }
}

private fun getFavoriteImageVector(boardgameItem: BoardgameItem): ImageVector {
    return if (boardgameItem.isFavorite) Icons.Filled.Favorite
    else Icons.Filled.FavoriteBorder
}


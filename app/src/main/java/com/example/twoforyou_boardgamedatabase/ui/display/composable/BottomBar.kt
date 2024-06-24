package com.example.twoforyou_boardgamedatabase.ui.display.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayViewModel

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    Text(
        modifier = modifier,
        text = "${viewModel.bottomBarLabelText} 게임 수 : ${viewModel.displayingBoardgameItemList.size} 개",
        fontSize = 18.sp
    )
}
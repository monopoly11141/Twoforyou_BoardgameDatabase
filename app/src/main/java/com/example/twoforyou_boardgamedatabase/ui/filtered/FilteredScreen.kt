package com.example.twoforyou_boardgamedatabase.ui.filtered

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedatabase.ui.detail.DetailViewModel
import com.example.twoforyou_boardgamedatabase.ui.display.composable.Boardgame

@Composable
fun FilteredScreen(
   navController: NavController,
   idList: List<Int> = emptyList(),
   viewModel: FilteredViewModel = hiltViewModel()
) {
   val state = viewModel.state.collectAsState().value

   viewModel.updateBoardgameList(idList)

   LazyColumn {
      items(state.boardgameList) {
         Boardgame(it, navController)
      }
   }

}
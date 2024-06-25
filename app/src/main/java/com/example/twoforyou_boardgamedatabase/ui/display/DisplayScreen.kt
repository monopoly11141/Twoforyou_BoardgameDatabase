package com.example.twoforyou_boardgamedatabase.ui.display

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedatabase.ui.display.composable.Boardgame
import com.example.twoforyou_boardgamedatabase.ui.display.composable.BoardgameDisplay
import com.example.twoforyou_boardgamedatabase.ui.display.composable.BottomBar
import com.example.twoforyou_boardgamedatabase.ui.display.composable.TopSearchBar


@Composable
fun DisplayScreen(
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    //updating all boardgame item data from api
    LaunchedEffect(Unit, state.boardgameItemList) {
        for (boardgameItem in state.boardgameItemList) {
            viewModel.updateBoardgameItemFromApi(boardgameItem)
        }
    }

    var dialogBoardgameUrl by remember { mutableStateOf("") }
    var hasSuccesfullyAddedboardgame by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton =
        {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape,
                modifier = Modifier
                    .imePadding()
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "추가")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 16.dp)
        ) {
            TopSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
            )

            BoardgameDisplay(
                modifier = Modifier
                    .weight(1f),
                navController = navController
            )

            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                TextField(
                    value = dialogBoardgameUrl,
                    onValueChange = { updatedUrl ->
                        dialogBoardgameUrl = updatedUrl
                    },
                    label = {
                        Text("보드게임 url")
                    }
                )

                Button(
                    onClick = {
                        hasSuccesfullyAddedboardgame = viewModel.insertBoardgameItemToDb(dialogBoardgameUrl)
                        showDialog = false
                        dialogBoardgameUrl = ""
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text("보드게임 추가")
                }
            }
        }
    }

    if (!hasSuccesfullyAddedboardgame) {
        Toast.makeText(LocalContext.current, "올바른 url을 입력하세요", Toast.LENGTH_SHORT).show()
        hasSuccesfullyAddedboardgame = true
    }

}
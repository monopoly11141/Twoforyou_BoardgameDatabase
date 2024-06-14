package com.example.twoforyou_boardgamedatabase.ui.display

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.twoforyou_boardgamedatabase.ui.display.composable.Boardgame
import com.example.twoforyou_boardgamedatabase.ui.display.util.DISPLAY_ORDER

@Composable
fun DisplayScreen(
    navController: NavController,
    viewModel: DisplayViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var dialogBoardgameUrl by remember { mutableStateOf("") }
    var hasSuccesfullyAddedboardgame by remember { mutableStateOf(true) }
    var searchString by remember { mutableStateOf("") }
    var boardgameListDisplay by remember { mutableStateOf(state.boardgameItemList) }
    var expandOrderDropDownMenu by remember { mutableStateOf(false) }
    var displayOrder by remember { mutableStateOf(DISPLAY_ORDER.ALPHABETICAL) }

    var showDialog by remember { mutableStateOf(false) }

    var boardgameItems by remember { mutableStateOf(boardgameListDisplay) }

    boardgameListDisplay = if (searchString.isBlank()) {
        state.boardgameItemList
    } else {
        viewModel.searchedBoardgame
    }

    boardgameItems = when (displayOrder) {
        DISPLAY_ORDER.ALPHABETICAL -> boardgameListDisplay
        DISPLAY_ORDER.FAVORITE -> boardgameListDisplay.filter {
            it.isFavorite
        }

        DISPLAY_ORDER.NON_FAVORITE -> boardgameListDisplay.filter {
            !it.isFavorite
        }
    }

    Scaffold(
        floatingActionButton =
        {
            ExtendedFloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape,
                modifier = Modifier
                    .imePadding()
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "추가")
                Text("보드게임 추가")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = searchString,
                onValueChange = { searchQuery ->
                    searchString = searchQuery
                    viewModel.searchBoardgame(searchString)
                },
                trailingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "순서",
                            modifier = Modifier
                                .padding(end = 4.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "검색",
                            modifier = Modifier
                                .clickable {
                                    expandOrderDropDownMenu = true
                                }
                        )
                        DropdownMenu(
                            expanded = expandOrderDropDownMenu,
                            onDismissRequest = { expandOrderDropDownMenu = false }
                        ) {
                            for (displayOrderEntry in DISPLAY_ORDER.entries) {
                                var dropDownItemText = ""
                                var dropDownItemImageVector = Icons.Filled.KeyboardArrowDown
                                when (displayOrderEntry) {
                                    DISPLAY_ORDER.ALPHABETICAL -> {
                                        dropDownItemText = "전체"
                                        dropDownItemImageVector = Icons.Filled.KeyboardArrowDown
                                    }

                                    DISPLAY_ORDER.FAVORITE -> {
                                        dropDownItemText = "즐겨찾기"
                                        dropDownItemImageVector = Icons.Filled.Favorite
                                    }

                                    DISPLAY_ORDER.NON_FAVORITE -> {
                                        dropDownItemText = "즐겨찾기 제외"
                                        dropDownItemImageVector = Icons.Filled.FavoriteBorder
                                    }
                                }
                                DropdownMenuItem(
                                    text = {
                                        Row {
                                            Text(dropDownItemText)
                                            Icon(
                                                imageVector = dropDownItemImageVector,
                                                contentDescription = dropDownItemText
                                            )
                                        }
                                    },
                                    onClick = {
                                        displayOrder = displayOrderEntry
                                    }
                                )
                            }

                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {

                items(
                    items = boardgameItems,
                    key = { boardgameItem ->
                        boardgameItem.id
                    }
                ) { boardgameItem ->

                    Boardgame(boardgameItem)

                    Divider(
                        thickness = 2.dp,
                        color = Color.Black
                    )
                }


            }
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
                        hasSuccesfullyAddedboardgame = viewModel.insertItems(dialogBoardgameUrl)
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
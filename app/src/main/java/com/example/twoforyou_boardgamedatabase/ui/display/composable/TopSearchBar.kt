package com.example.twoforyou_boardgamedatabase.ui.display.composable


import android.media.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twoforyou_boardgamedatabase.R
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayViewModel
import com.example.twoforyou_boardgamedatabase.ui.display.util.DISPLAY_ORDER

@Composable
fun TopSearchBar(
    modifier : Modifier = Modifier,
    viewModel: DisplayViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current
    var expandOrderDropDownMenu by remember { mutableStateOf(false) }
    var boardgameSearchQuery by remember { mutableStateOf("") }
    var displayIcon by remember { mutableStateOf(Icons.Filled.KeyboardArrowDown) }

    viewModel.updateDisplayingBoardgameItemList(boardgameSearchQuery)

    displayIcon = when (viewModel.displayOrder) {
        DISPLAY_ORDER.ALPHABETICAL -> Icons.Filled.KeyboardArrowDown
        DISPLAY_ORDER.FAVORITE -> Icons.Filled.Favorite
        DISPLAY_ORDER.NON_FAVORITE -> Icons.Filled.FavoriteBorder
        DISPLAY_ORDER.RANKING ->  Icons.Filled.List
        DISPLAY_ORDER.WEIGHT ->  Icons.Filled.List
    }

    Row(modifier) {
        TextField(
            value = boardgameSearchQuery,
            onValueChange = { searchQuery ->
                boardgameSearchQuery = searchQuery
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "검색",
                        modifier = Modifier
                            .padding(end = 4.dp)
                    )
                    Icon(
                        imageVector = displayIcon,
                        contentDescription = "정렬",
                        modifier = Modifier
                            .clickable {
                                expandOrderDropDownMenu = true
                            }
                            .padding(end = 4.dp)
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
                                DISPLAY_ORDER.RANKING -> {
                                    dropDownItemText = "긱 순위"
                                    dropDownItemImageVector = Icons.Filled.List
                                }
                                DISPLAY_ORDER.WEIGHT -> {
                                    dropDownItemText = "복잡도"
                                    dropDownItemImageVector = Icons.Filled.List
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
                                    viewModel.updateDisplayOrder(displayOrderEntry)
                                    expandOrderDropDownMenu = false
                                }
                            )
                        }
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
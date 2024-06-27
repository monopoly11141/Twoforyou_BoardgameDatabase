package com.example.twoforyou_boardgamedatabase.ui.filtered

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun FilteredScreen(
   navController: NavController,
   idList: List<Int> = emptyList()
) {
   Text(idList.toString())
}
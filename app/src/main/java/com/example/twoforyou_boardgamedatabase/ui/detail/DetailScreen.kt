package com.example.twoforyou_boardgamedatabase.ui.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.example.twoforyou_boardgamedatabase.navigation.Screen
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getBoardgameById(id)
    }
    val boardgameItem = viewModel.boardgameItem
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(model = boardgameItem.imageUrl, contentDescription = "보드게임 사진")
    }
    Log.d("TAG", viewModel.boardgameItem.toString())
}
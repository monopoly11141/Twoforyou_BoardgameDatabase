package com.example.twoforyou_boardgamedatabase.ui.detail

import android.content.ContentValues.TAG
import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.twoforyou_boardgamedatabase.R
import com.example.twoforyou_boardgamedatabase.navigation.Screen
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.w3c.dom.Text
import java.math.RoundingMode


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

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(boardgameItem.imageUrl).build(),
        contentScale = ContentScale.Fit
    )

    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.HALF_UP.ordinal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.detail_screen_background))
            .paint(
                painter,
                contentScale = ContentScale.Fit,
                alpha = 0.2f,
                alignment = Alignment.TopCenter
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = boardgameItem.koreanName.ifEmpty { boardgameItem.englishName },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        GridRow(key = "긱 순위", value = "${boardgameItem.ranking} 위")
        GridRow(key = "인원", value = "${boardgameItem.minPlayersValue}~${boardgameItem.maxPlayersValue} 명")
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Box(modifier = Modifier
                .weight(0.5f)
                .padding(start = 4.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "평점",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White
                )
            }
            LinearProgressIndicator(
                progress = boardgameItem.bayesAverageValue / 10f,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = df.format(boardgameItem.bayesAverageValue),
                color = Color.White
            )

        }
        GridRow(key = "복잡도", value = boardgameItem.averageWeight.toString())
        GridRow(key = "복잡도", value = boardgameItem.averageWeight.toString())


    }
    Log.d("TAG", viewModel.boardgameItem.toString())
}

@Composable
fun GridRow(key: String, value : String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(modifier = Modifier
            .weight(0.5f)
            .padding(start = 4.dp),
            contentAlignment = Alignment.Center) {
            Text(
                text = key,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White
            )
        }
        Box(modifier = Modifier
            .weight(0.5f)
            .padding(end = 4.dp),
            contentAlignment = Alignment.Center) {
            Text(
                text = value,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White
            )
        }
    }
}
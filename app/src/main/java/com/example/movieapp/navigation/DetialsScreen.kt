package com.example.movieapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.models.Details
import com.example.movieapp.viewModal.MovieViewModal

@Composable
fun DetailsScreen(id: Int) {
    val movieViewModal = viewModel<MovieViewModal>()
    LaunchedEffect(id) {
        movieViewModal.id = id
        movieViewModal.getdetailsById()
    }

    val state = movieViewModal.state
    val details = state.detailsData

    /*if (details == null) {
        Text(text = "Loading...", fontSize = 24.sp, color = Color.White)
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        BackGroundPoster(details = details)
        ForeGroundPoster(details = details)
        Column(
            Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
           Text(
               text = details.title,
               modifier = Modifier.fillMaxSize(),
               lineHeight = 40.sp,
               textAlign = TextAlign.Center,
               fontSize = 38.sp
           )


            Rating(details = details, modifier=Modifier)
            TextBuilder(icon = Icons.Filled.Info, title = "Summery:", bodyText = details.plot)
            TextBuilder(icon = Icons.Filled.Person, title = "Actors:", bodyText = details.actor)
            ImageRow(details = details)
        }

    }*/

    when {
        state.isLoading -> {
            Text(text = "Loading...", fontSize = 24.sp, color = Color.White)
        }
        state.detailsData == null -> {
            Text(text = "No details available", fontSize = 24.sp, color = Color.White)
        }
        else -> {
            val details = state.detailsData!!
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                BackGroundPoster(details = details)
                ForeGroundPoster(details = details)
                Column(
                    Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = details.title,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center,
                        fontSize = 38.sp
                    )

                    Rating(details = details, modifier = Modifier)
                    TextBuilder(icon = Icons.Filled.Info, title = "Summary:", bodyText = details.plot)
                    TextBuilder(icon = Icons.Filled.Person, title = "Actors:", bodyText = details.actor)
                    ImageRow(details = details)
                }
            }
        }
    }
}

@Composable
fun ImageRow(details: Details) {
    if (details.images.isNotEmpty()) {
        LazyRow {
            items(details.images.size) {
                AsyncImage(
                    model = details.images[it], contentDescription = "",
                    Modifier
                        .padding(6.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
@Composable
fun BackGroundPoster(details: Details) {
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
){
    AsyncImage(
        model = details.poster, contentDescription = details.title,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.6f)
    )
    Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.DarkGray
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    )
}
}
@Composable
fun ForeGroundPoster(details: Details) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .padding(top=80.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(
            model = details.poster, contentDescription = details.title,
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color(0XB91A1B1B)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun Rating(details: Details, modifier: Modifier){
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Icon(imageVector = Icons.Filled.Star,contentDescription="", tint = Color.White)

        Text(
            text = details  .rated ?: "N/A",
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.width(25.dp))
        Icon(
            painter = painterResource(id = R.drawable.time_24),
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = details.runtime ?: "Unknown",
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.width(25.dp))
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = details.released ?: "N/A",
            modifier.padding(start = 6.dp),
            color = Color.White
        )
    }
}

@Composable
fun TextBuilder(icon: ImageVector,title : String, bodyText:String){
    Row {
        Icon(imageVector = icon,
            contentDescription=title,
            tint = Color.White
        )

        Text(
            text = title,
            Modifier.padding(start = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
    Text(text = bodyText , color = Color.White)

}


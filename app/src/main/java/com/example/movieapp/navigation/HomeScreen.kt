package com.example.movieapp.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movieapp.models.Data
import com.example.movieapp.viewModal.MovieViewModal


@Composable
fun HomeScreen(navController: NavHostController) {
    val movieViewModal = viewModel<MovieViewModal>()
    val state = movieViewModal.state
    Scaffold(
        modifier = Modifier.background(Color.Transparent), topBar = {
            TopBar()
        }, content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(
                        Color.Transparent
                    ),
                content = {
                    items(state.movies.size) {
                        if (it >= state.movies.size - 1 && !state.endReached && !state.isLoading) {
                            movieViewModal.loadNextItems()
                        }
                        ItemUi(
                            itemIndex = it, movieList = state.movies, navController = navController
                        )
                    }
                    item(state.isLoading) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = ProgressIndicatorDefaults.circularColor)
                        }
                        if(!state.error.isNullOrEmpty()){
                            Toast.makeText(LocalContext.current,state.error, Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }, containerColor = Color.Transparent

    )

}

@Composable
fun ItemUi(itemIndex: Int, movieList: List<Data>, navController: NavHostController) {
    // Log.d("Image URL", movieList[itemIndex].poster ?: "No URL")
    val context = LocalContext.current

    /*val imageLoader = remember {
        ImageLoader.Builder(context).crossfade(true).memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED).build()
    }
    val imageRequest = remember {
        ImageRequest.Builder(context).data(movieList[itemIndex].poster).crossfade(true)
            .error(android.R.drawable.ic_dialog_alert).build()
    }*/

    Card(
        Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                navController.navigate("Details Screen/${movieList[itemIndex].id}")
            }, elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            /* AsyncImage(
                 model = imageRequest,//movieList[itemIndex].poster,
                 contentDescription = movieList[itemIndex].title,
                 imageLoader = imageLoader,
                 modifier = Modifier
                     .fillMaxSize()
                     .clip(RoundedCornerShape(10.dp)),
                 contentScale = ContentScale.Crop
             )*/

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(movieList[itemIndex].poster)
                    .build(),

                contentDescription = movieList[itemIndex].title,
                contentScale = ContentScale.Fit,//ContentScale.Crop,
                modifier = Modifier//.size(200.dp) //size new added
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.LightGray.copy(.7f))
                    .padding(6.dp)
            ) {
                Text(
                    text = movieList[itemIndex].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0XFFFC5A03), offset = Offset(1f, 1f), blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.align(Alignment.End)) {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = " ")
                    Text(
                        text = movieList[itemIndex].imdb_rating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee(),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Movie App") }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(.7f)
        )
    )
}

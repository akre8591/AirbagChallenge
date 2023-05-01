package com.example.airbarchallenge.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.airbarchallenge.R
import com.example.airbarchallenge.domain.models.TvShow
import com.example.airbarchallenge.presentation.ListRatedTvShowsState
import com.example.airbarchallenge.presentation.MainViewModel
import com.example.airbarchallenge.utils.shimmerBackground

@Composable
fun ListScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val screenTitle = stringResource(id = R.string.list_title)
    val errorMessage = stringResource(id = R.string.error_message)

    LaunchedEffect(Unit) {
        viewModel.setTitle(screenTitle)
    }

    when (val showsItems = viewModel.topRatedShowList.value) {
        is ListRatedTvShowsState.Success -> {
            val tvShows = showsItems.list ?: emptyList()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(tvShows) { tvShows ->
                    Row(Modifier.padding(8.dp)) {
                        ItemLayout(tvShows, tvShows.id, navController)
                    }
                }
            }
        }

        is ListRatedTvShowsState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .shimmerBackground(RoundedCornerShape(1.dp)),
            )
        }

        is ListRatedTvShowsState.Error -> {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun ItemLayout(
    show: TvShow?,
    index: Int?,
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .clickable {
                navController.navigate("details/$index")
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = show?.posterPath),
            contentDescription = show?.name.orEmpty(),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = show?.name.orEmpty(),
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}


package com.example.airbarchallenge.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.airbarchallenge.R
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.presentation.ListRatedTvShowsState
import com.example.airbarchallenge.utils.completeImagePath
import com.example.airbarchallenge.utils.shimmerBackground
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun ListScreen(
    navController: NavHostController,
    state: State<ListRatedTvShowsState>
) {
    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_message)

    when (val showItems = state.value) {
        is ListRatedTvShowsState.Success -> {
            ListItems(tvShows = showItems.list.orEmpty(), navController = navController)
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
            if (showItems.list.isNullOrEmpty()) {
                EmptyContent()
            } else {
                ListItems(tvShows = showItems.list, navController = navController)
            }

        }
    }
}

@Composable
fun ListItems(
    tvShows: List<ShowEntity>,
    navController: NavHostController
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(top = 60.dp)
            .background(color = Color.LightGray),
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

@Composable
fun ItemLayout(
    show: ShowEntity?,
    index: Int?,
    navController: NavHostController
) {
    Card {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("details/$index")
                }
        ) {
            AsyncImage(
                model = show?.posterPath?.completeImagePath(),
                placeholder = painterResource(R.drawable.default_photo),
                contentDescription = show?.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = show?.name.orEmpty(),
                color = Color.Black,
                fontSize = 14.sp,

                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(20.dp)
            )
            Row() {
                RatingBar(
                    modifier = Modifier
                        .padding(4.dp),
                    rating = show?.voteAverage?.div(2) ?: 0.0
                )
                Text(text = show?.voteAverage.toString(), modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }
        if (halfStar) {
            Icon(
                painter = painterResource(id = R.drawable.outline_star_half_24),
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(id = R.drawable.outline_star_outline_24),
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}



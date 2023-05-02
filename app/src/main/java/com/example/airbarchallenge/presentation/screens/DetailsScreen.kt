package com.example.airbarchallenge.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.airbarchallenge.R
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.presentation.MainViewModel
import com.example.airbarchallenge.presentation.SingleShowState
import com.example.airbarchallenge.utils.Constants.DETAILS_TEST_TAG
import com.example.airbarchallenge.utils.completeImagePath
import com.example.airbarchallenge.utils.shimmerBackground

@Composable
fun DetailsScreen(
    id: String,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_message)

    LaunchedEffect(Unit) {
        viewModel.getShowById(id.toInt())
    }

    when (val showEntity = viewModel.showEntity.value) {
        is SingleShowState.Success -> {
            ShowDetails(showEntity = showEntity.show)
        }

        is SingleShowState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .shimmerBackground(RoundedCornerShape(1.dp)),
            )
        }

        is SingleShowState.Error -> {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            EmptyContent()
        }
    }
}

@Composable
fun ShowDetails(showEntity: ShowEntity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .testTag(DETAILS_TEST_TAG)
        ) {
            AsyncImage(
                model = showEntity.posterPath?.completeImagePath(),
                placeholder = painterResource(R.drawable.baseline_downloading_24),
                error = painterResource(R.drawable.baseline_error_24),
                contentDescription = showEntity.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 60.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = showEntity.name.orEmpty(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(text = showEntity.overview.orEmpty(), lineHeight = 24.sp)
            }
        }
    }
}
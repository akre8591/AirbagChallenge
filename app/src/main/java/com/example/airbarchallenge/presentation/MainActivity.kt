package com.example.airbarchallenge.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.airbarchallenge.presentation.screens.DetailsScreen
import com.example.airbarchallenge.presentation.screens.ListScreen
import com.example.airbarchallenge.presentation.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTopRatedShowList()
        setContent {
            TopRatedListScreen(viewModel = viewModel)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRatedListScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    var canPop by remember {
        mutableStateOf(false)
    }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        canPop = controller.previousBackStackEntry != null
    }

    val navigationIcon: (@Composable () -> Unit)? =
        if (canPop) {
            {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        } else {
            null
        }

    Scaffold(
        topBar = {
            navigationIcon?.let {
                TopAppBar(
                    title = { Text(viewModel.screenTitle.value) },
                    navigationIcon = it,
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40)
                )
            } ?: run {
                TopAppBar(
                    title = { Text(viewModel.screenTitle.value) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40)
                )
            }
        },
        content = {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { ListScreen(navController, viewModel) }
                composable("details/{listId}") { backStackEntry ->
                    backStackEntry.arguments?.getString("listId")
                        ?.let { DetailsScreen(it, navController, viewModel) }
                }
            }
        }
    )
}


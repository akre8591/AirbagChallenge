package com.example.airbarchallenge.composeComponents

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.presentation.screens.ListItems
import com.example.airbarchallenge.utils.Constants.LIST_ITEMS_TEST_TAG
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    private val localTvShowList =
        listOf(
            ShowEntity(0, "test", 8.2, "fake_path", "fake_overview"),
            ShowEntity(1, "test", 9.2, "fake_path", "fake_overview")
        )

    @Test
    fun showListScreen() {

        composeRule.setContent {
            ListItems(
                tvShows = localTvShowList,
                navController = navController
            )
        }

        composeRule.onNodeWithTag(LIST_ITEMS_TEST_TAG, true)
            .assertIsDisplayed()

    }

}
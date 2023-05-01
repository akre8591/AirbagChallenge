package com.example.airbarchallenge.composeComponents

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.airbarchallenge.data.db.ShowEntity
import com.example.airbarchallenge.presentation.screens.ShowDetails
import com.example.airbarchallenge.utils.Constants.DETAILS_TEST_TAG
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    val entity = ShowEntity(1, "test", 9.2, "fake_path", "fake_overview")

    @Test
    fun showDetailsScreen() {

        composeRule.setContent {
            ShowDetails(entity)
        }

        composeRule.onNodeWithTag(DETAILS_TEST_TAG, true)
            .assertIsDisplayed()
    }

}
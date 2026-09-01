package com.example.peego

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.peego.ui.components.TopSearchBar
import org.junit.Rule
import org.junit.Test

class ExploreScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun barraDeBuscaExibeTextoPlaceholder() {
        composeTestRule.setContent {
            TopSearchBar(query = "", onQueryChange = {}, onFilterClick = {})
        }
        composeTestRule.onNodeWithText("Onde você precisa ir?").assertIsDisplayed()
    }
}

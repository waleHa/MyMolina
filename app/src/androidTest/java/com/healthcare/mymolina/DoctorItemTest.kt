package com.healthcare.mymolina

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.ui.physician.DoctorItem
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import org.junit.Rule
import org.junit.Test

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText


class DoctorItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDoctorItemDisplaysCorrectly() {
        val doctor = Doctor(
            firstName = "John",
            lastName = "Doe",
            speciality = "Cardiology",
            location = com.healthcare.mymolina.domain.remotemodel.doctor.Location(
                city = "Seattle",
                state = "WA",
                street = "123 Main St",
                zip = "98101"
            ),
            email = "john.doe@example.com",
            phone = "123-456-7890",
            profileUrl = ""
        )

        composeTestRule.setContent {
            MyMolinaTheme {
                DoctorItem(doctor = doctor)
            }
        }

        // Check if the doctor's name is displayed
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        // Check if the doctor's specialty is displayed
        composeTestRule.onNodeWithText("Specialty: Cardiology").assertIsDisplayed()
        // Check if the doctor's location is displayed
        composeTestRule.onNodeWithText("Location: 123 Main St, Seattle, WA, 98101").assertIsDisplayed()
        // Check if the doctor's email is displayed
        composeTestRule.onNodeWithText("Email: john.doe@example.com").assertIsDisplayed()
        // Check if the doctor's phone is displayed
        composeTestRule.onNodeWithText("Phone: 123-456-7890").assertIsDisplayed()
    }
}

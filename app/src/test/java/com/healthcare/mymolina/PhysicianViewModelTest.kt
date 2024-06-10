package com.healthcare.mymolina

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.remotemodel.doctor.Location
import com.healthcare.mymolina.domain.usecase.GetPhysicianListUseCase
import com.healthcare.mymolina.ui.physician.PhysicianViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever



@ExperimentalCoroutinesApi
class PhysicianViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetPhysicianListUseCase
    private lateinit var viewModel: PhysicianViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock()
        viewModel = PhysicianViewModel(useCase)
    }

    @Test
    fun `getPhysicianList should update physicianListSuccess on success`() = runTest {
        val doctors = listOf(
            Doctor(
                email = "john.doe@example.com",
                firstName = "John",
                lastName = "Doe",
                speciality = "Cardiology",
                location = Location(
                    city = "Seattle",
                    state = "WA",
                    street = "123 Main St",
                    zip = "98101"
                ),
                phone = "123-456-7890",
                profileUrl = ""
            )
        )
        whenever(useCase()).thenReturn(doctors)

        viewModel.getPhysicianList()

        advanceUntilIdle()

        assertEquals(doctors, viewModel.physicianListSuccess.value)
        assertFalse(viewModel.loading.value)
    }

    @Test
    fun `getPhysicianList should set loading to true and then false`() = runTest {
        whenever(useCase()).thenReturn(emptyList())

        viewModel.getPhysicianList()

        assertTrue(viewModel.loading.value)

        advanceUntilIdle()

        assertFalse(viewModel.loading.value)
    }

    @Test
    fun `getPhysicianList should handle exception`() = runTest {
        whenever(useCase()).thenThrow(RuntimeException("Network Error"))

        viewModel.getPhysicianList()

        advanceUntilIdle()

        assertTrue(viewModel.physicianListSuccess.value.isEmpty())
        assertFalse(viewModel.loading.value)
    }
}

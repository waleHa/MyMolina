package com.healthcare.mymolina

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.remotemodel.doctor.Location
import com.healthcare.mymolina.domain.usecase.GetPhysicianListUseCase
import com.healthcare.mymolina.ui.physician.PhysicianViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.net.UnknownHostException


//@ExperimentalCoroutinesApi
//class PhysicianViewModelTest {
//    // Rule to allow LiveData to work synchronously in tests
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    // Mock of the use case
//    private val useCase: GetPhysicianListUseCase = mock()
//
//    // The ViewModel to be tested
//    private lateinit var viewModel: PhysicianViewModel
//
//    @Before
//    fun setup() {
//        // Set the main dispatcher to a test dispatcher
//        Dispatchers.setMain(StandardTestDispatcher())
//        // Initialize the ViewModel with the mock use case
//        viewModel = PhysicianViewModel(useCase)
//    }
//
//    @After
//    fun tearDown() {
//        // Reset the main dispatcher to the original Main dispatcher
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `getPhysicianList emits success list`() = runTest {
//        // Prepare a mock list of doctors
//        val remoteDoctor: List<Doctor> = listOf(
//            Doctor(
//                email = "john.doe@example.com",
//                firstName = "John",
//                gender = "Male",
//                id = 1,
//                isPhysician = true,
//                lastName = "Doe",
//                location = Location(
//                    city = "New York",
//                    state = "NY",
//                    street = "123 Main St",
//                    zip = "10001"
//                ),
//                phone = "555-1234",
//                profileUrl = "http://example.com/john_doe",
//                speciality = "Cardiology"
//            ),
//            Doctor(
//                email = "jane.smith@example.com",
//                firstName = "Jane",
//                gender = "Female",
//                id = 2,
//                isPhysician = true,
//                lastName = "Smith",
//                location = Location(
//                    city = "Los Angeles",
//                    state = "CA",
//                    street = "456 Elm St",
//                    zip = "90001"
//                ),
//                phone = "555-5678",
//                profileUrl = "http://example.com/jane_smith",
//                speciality = "Dermatology"
//            )
//        )
//
//        // Mock the use case to return the prepared list of doctors
//        whenever(useCase()).thenReturn(remoteDoctor)
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the emitted list of doctors is as expected
//        val result = viewModel.physicianListSuccess.first()
//        assertEquals(remoteDoctor, result)
//    }
//
//    @Test
//    fun `getPhysicianList sets loading to false after success`() = runTest {
//        // Prepare an empty list of doctors
//        val remoteDoctor: List<Doctor> = emptyList()
//        // Mock the use case to return the empty list
//        whenever(useCase()).thenReturn(remoteDoctor)
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the loading state is set to false after the operation
//        val loading = viewModel.loading.first()
//        assertEquals(false, loading)
//    }
//
//    @Test
//    fun `getPhysicianList emits error message on UnknownHostException`() = runTest {
//        // Mock the use case to throw an UnknownHostException
//        whenever(useCase()).thenThrow(UnknownHostException())
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the error message is set correctly for network error
//        val error = viewModel.error.first()
//        assertEquals("Network error: Unable to resolve host. Please check your internet connection.", error)
//    }
//
//    @Test
//    fun `getPhysicianList sets loading to false on UnknownHostException`() = runTest {
//        // Mock the use case to throw an UnknownHostException
//        whenever(useCase()).thenThrow(UnknownHostException())
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the loading state is set to false after the operation
//        val loading = viewModel.loading.first()
//        assertEquals(false, loading)
//    }
//
//    @Test
//    fun `getPhysicianList emits generic error message on generic exception`() = runTest {
//        // Mock the use case to throw a generic exception
//        whenever(useCase()).thenThrow(RuntimeException("Some error"))
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the error message is set correctly for a generic error
//        val error = viewModel.error.first()
//        assertEquals("An unexpected error occurred: Some error", error)
//    }
//
//    @Test
//    fun `getPhysicianList sets loading to false on generic exception`() = runTest {
//        // Mock the use case to throw a generic exception
//        whenever(useCase()).thenThrow(RuntimeException("Some error"))
//
//        // Call the method in the ViewModel
//        viewModel.getPhysicianList()
//
//        // Assert that the loading state is set to false after the operation
//        val loading = viewModel.loading.first()
//        assertEquals(false, loading)
//    }
//}
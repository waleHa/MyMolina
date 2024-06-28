package com.healthcare.mymolina

import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.remotemodel.doctor.Location
import com.healthcare.mymolina.domain.remotemodel.doctor.Physician
import com.healthcare.mymolina.domain.repository.MolinaRepository
import com.healthcare.mymolina.domain.usecase.GetPhysicianListUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.assertFailsWith
import com.healthcare.mymolina.domain.remotemodel.doctor.Result

class GetPhysicianListUseCaseTest {

    private lateinit var repository: MolinaRepository
    private lateinit var useCase: GetPhysicianListUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetPhysicianListUseCase(repository)
    }

    @Test
    fun `invoke returns doctor list when repository call is successful`() = runTest {
        val remoteDoctor: List<Doctor?> = listOf(
            Doctor(
                email = "john.doe@example.com",
                firstName = "John",
                gender = "Male",
                id = 1,
                isPhysician = true,
                lastName = "Doe",
                location = Location(
                    city = "New York",
                    state = "NY",
                    street = "123 Main St",
                    zip = "10001"
                ),
                phone = "555-1234",
                profileUrl = "http://example.com/john_doe",
                speciality = "Cardiology"
            ),
            Doctor(
                email = "jane.smith@example.com",
                firstName = "Jane",
                gender = "Female",
                id = 2,
                isPhysician = true,
                lastName = "Smith",
                location = Location(
                    city = "Los Angeles",
                    state = "CA",
                    street = "456 Elm St",
                    zip = "90001"
                ),
                phone = "555-5678",
                profileUrl = "http://example.com/jane_smith",
                speciality = "Dermatology"
            )
            // Add more doctors as needed
        )
        val remotePhysician = Physician(null, listOf(Result(remoteDoctor)))

        whenever(repository.getPhysicianList()).thenReturn(Response.success(remotePhysician))

        val result = useCase.invoke()
        assertEquals(remoteDoctor.filterNotNull(), result)
    }

    @Test
    fun `invoke throws exception when repository call fails`() = runTest {
        whenever(repository.getPhysicianList()).thenThrow(RuntimeException())

        assertFailsWith<RuntimeException> {
            useCase.invoke()
        }
    }
}
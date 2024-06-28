package com.healthcare.mymolina

import com.healthcare.mymolina.data.network.MolinaRemoteService
import com.healthcare.mymolina.data.repository.MolinaRepositoryImpl
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.remotemodel.doctor.Location
import com.healthcare.mymolina.domain.remotemodel.doctor.Physician
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import com.healthcare.mymolina.domain.remotemodel.doctor.Result
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.assertFailsWith

class MolinaRepositoryImplTest {

    private lateinit var service: MolinaRemoteService
    private lateinit var repository: MolinaRepositoryImpl

    @Before
    fun setup() {
        service = mock<MolinaRemoteService>()
        repository = MolinaRepositoryImpl(service)
    }

    @Test
    fun getDoctorModelList() = runTest {
        val remoteDoctor: List<Doctor?> = mock<List<Doctor?>>()
//        val remoteDoctor: List<Doctor?> = listOf(
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
//            // Add more doctors as needed
//        )
        val remotePhysician = Physician(null, listOf(Result(remoteDoctor)))

        whenever(service.getPhysicianList()).thenReturn(Response.success(remotePhysician))

        val result = requireNotNull(repository.getPhysicianList().body()?.results?.first()?.doctors)
        assertEquals(remoteDoctor, result)
    }

    @Test
    fun getDoctorModelExceptions() = runTest {
        whenever(service.getPhysicianList()).thenThrow(RuntimeException())
//        try {
//            service.getPhysicianList().body()
//        } catch (e: Exception) {
//            TestCase.assertTrue(e is Throwable)
//        }

        assertFailsWith<Throwable>{
            service.getPhysicianList().body()
        }

    }
}
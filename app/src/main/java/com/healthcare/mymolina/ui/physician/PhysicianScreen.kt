package com.healthcare.mymolina.ui.physician


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.ui.PhysicianViewModel
import com.healthcare.mymolina.ui.component.DropdownMenuComponent
import com.healthcare.mymolina.ui.component.IconButtonComponent
import com.healthcare.mymolina.ui.component.ImageComponent
import com.healthcare.mymolina.ui.component.SpacerComponent
import com.healthcare.mymolina.ui.component.TextComponent
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PhysicianScreen(navController: NavController) {
    val viewModel: PhysicianViewModel = hiltViewModel()
    val physicianList by viewModel.physicianListSuccess.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedSpecialty by remember { mutableStateOf("All") }
    var selectedLocation by remember { mutableStateOf("All") }
    var filteredPhysicians by remember { mutableStateOf(physicianList) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }

    // Get unique specialties and locations, sorted alphabetically
    val specialties = remember(physicianList) {
        (listOf("All") + physicianList.mapNotNull { it.speciality }.distinct()).sorted()
    }
    val locations = remember(physicianList) {
        (listOf("All") + physicianList.mapNotNull { it.location?.city }.distinct()).sorted()
    }

    // Dynamic dropdown options based on selection
    val filteredSpecialties = remember(selectedLocation, physicianList) {
        if (selectedLocation == "All") {
            specialties
        } else {
            (listOf("All") + physicianList.filter {
                it.location?.city == selectedLocation
            }.mapNotNull { it.speciality }.distinct()).sorted()
        }
    }

    val filteredLocations = remember(selectedSpecialty, physicianList) {
        if (selectedSpecialty == "All") {
            locations
        } else {
            (listOf("All") + physicianList.filter {
                it.speciality == selectedSpecialty
            }.mapNotNull { it.location?.city }.distinct()).sorted()
        }
    }

    // Filtering logic
    LaunchedEffect(physicianList, searchQuery, selectedSpecialty, selectedLocation) {
        filteredPhysicians = physicianList.filter {
            (searchQuery.isEmpty() || it.firstName?.contains(
                searchQuery,
                true
            ) == true || it.lastName?.contains(searchQuery, true) == true) &&
                    (selectedSpecialty == "All" || it.speciality == selectedSpecialty) &&
                    (selectedLocation == "All" || it.location?.city?.contains(
                        selectedLocation,
                        true
                    ) == true ||
                            it.location?.state?.contains(selectedLocation, true) == true ||
                            it.location?.street?.contains(selectedLocation, true) == true ||
                            it.location?.zip?.contains(selectedLocation, true) == true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF8FFFF))
        ) {
            TextComponent(
                text = "Find a Doctor",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            )

            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        delay(300)  // debounce time for search
                        // Filter physicians based on search query
                        filteredPhysicians = physicianList.filter { doctor ->
                            doctor.firstName!!.contains(searchQuery, ignoreCase = true) ||
                                    doctor.lastName!!.contains(searchQuery, ignoreCase = true)
                        }
                    }
                },


                label = { Text("Search by name") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchJob?.cancel()
                        searchJob = coroutineScope.launch {
                            // No need to call viewModel.searchTracks() as we're filtering locally
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp),
            )

            // Specialty Dropdown
            DropdownMenuComponent(
                label = "Select Specialty",
                options = filteredSpecialties,
                selectedOption = selectedSpecialty,
                onOptionSelected = { selectedSpecialty = it },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            SpacerComponent(height = 16.dp)

            // Location Dropdown
            DropdownMenuComponent(
                label = "Select Location",
                options = filteredLocations,
                selectedOption = selectedLocation,
                onOptionSelected = { selectedLocation = it },
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

//        SpacerComponent(height = 16.dp)

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .padding(16.dp)
            ) {
                items(filteredPhysicians) { doctor ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        DoctorItem(doctor)
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorItem(doctor: Doctor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            ImageComponent(
                imageUrl = doctor.profileUrl ?: "",
                contentDescription = "Doctor Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TextComponent(
                    text = "${doctor.firstName} ${doctor.lastName}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextComponent(
                    text = "Specialty: ${doctor.speciality}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextComponent(
                    text = "Location: ${doctor.location?.street}, ${doctor.location?.city}, ${doctor.location?.state}, ${doctor.location?.zip}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextComponent(
                    text = "Email: ${doctor.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextComponent(
                    text = "Phone: ${doctor.phone}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewPhysicianScreen() {
    MyMolinaTheme {
        PhysicianScreen(navController = rememberNavController())
    }
}

package com.healthcare.mymolina.ui.branch.branch


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.domain.remotemodel.doctor.Location
import com.healthcare.mymolina.ui.TopAppBarWithBack
import com.healthcare.mymolina.ui.component.*
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun BranchLocatorScreen(navController: NavController,title:String="Branches") {
    val viewModel: BranchLocatorViewModel = hiltViewModel()
    val branchList by viewModel.branchList.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("All") }
    var filteredBranches by remember { mutableStateOf(branchList) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }

    // Get unique branch cities, sorted alphabetically
    val cities = remember(branchList) {
        (listOf("All") + branchList.mapNotNull { it.city }.distinct()).sorted()
    }

    // Filtering logic
    LaunchedEffect(branchList, searchQuery, selectedCity) {
        filteredBranches = branchList.filter {
            (searchQuery.isEmpty() || it.city?.contains(
                searchQuery,
                true
            ) == true || it.state?.contains(searchQuery, true) == true || it.street?.contains(
                searchQuery,
                true
            ) == true || it.zip?.contains(searchQuery, true) == true) &&
                    (selectedCity == "All" || it.city == selectedCity)
        }
    }
    Scaffold(
        topBar = { TopAppBarWithBack(navController = navController, title = title) }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFCCFFFF))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Branch Locator",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        searchJob?.cancel()
                        searchJob = coroutineScope.launch {
                            delay(300)  // debounce time for search
                            filteredBranches = branchList.filter { branch ->
                                branch.city!!.contains(searchQuery, ignoreCase = true) ||
                                        branch.state!!.contains(searchQuery, ignoreCase = true) ||
                                        branch.street!!.contains(searchQuery, ignoreCase = true) ||
                                        branch.zip!!.contains(searchQuery, ignoreCase = true)
                            }
                        }
                    },
                    label = { Text("Search branches") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Address Dropdown
                DropdownMenuComponent(
                    label = "Select City",
                    options = cities,
                    selectedOption = selectedCity,
                    onOptionSelected = { selectedCity = it }
                )
            }

            SpacerComponent(height = 16.dp)

            if (loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                        .padding(horizontal = 16.dp)
                ) {
                    items(filteredBranches) { branch ->
                        BranchItem(branch)
                    }
                }
            }
        }
    }
}

@Composable
fun BranchItem(branch: Location) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Street: ${branch.street ?: ""}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "City: ${branch.city ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "State: ${branch.state ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Zip: ${branch.zip ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewBranchLocatorScreen() {
    MyMolinaTheme {
        BranchLocatorScreen(navController = rememberNavController())
    }
}

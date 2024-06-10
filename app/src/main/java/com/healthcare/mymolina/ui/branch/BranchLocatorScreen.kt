package com.healthcare.mymolina.ui.branch


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.mymolina.domain.remotemodel.doctor.Doctor
import com.healthcare.mymolina.ui.branch.model.Branch
import com.healthcare.mymolina.ui.branch.viewmodel.BranchLocatorViewModel
import com.healthcare.mymolina.ui.component.*
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun BranchLocatorScreen(navController: NavController) {
    val viewModel: BranchLocatorViewModel = hiltViewModel()
    val branchList by viewModel.branchList.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedBranch by remember { mutableStateOf("All") }
    var filteredBranches by remember { mutableStateOf(branchList) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob: Job? by remember { mutableStateOf(null) }

    // Get unique branch addresses, sorted alphabetically
    val addresses = remember(branchList) {
        (listOf("All") + branchList.mapNotNull { it.location?.city }.distinct()).sorted()
    }

    // Filtering logic
    LaunchedEffect(branchList, searchQuery, selectedBranch) {
        filteredBranches = branchList.filter {
            (searchQuery.isEmpty() || it.firstName?.contains(searchQuery, true) == true || it.lastName?.contains(searchQuery, true) == true) &&
                    (selectedBranch == "All" || it.location?.city == selectedBranch)
        }
    }

    Column(
        modifier = Modifier
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
                            branch.firstName!!.contains(searchQuery, ignoreCase = true) ||
                                    branch.lastName!!.contains(searchQuery, ignoreCase = true) ||
                                    branch.location?.city!!.contains(searchQuery, ignoreCase = true)
                        }
                    }
                },
                label = { Text("Search branches") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Address Dropdown
            DropdownMenuComponent(
                label = "Select Address",
                options = addresses,
                selectedOption = selectedBranch,
                onOptionSelected = { selectedBranch = it }
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
                    .padding(16.dp)
            ) {
                items(filteredBranches) { branch ->
                    BranchItem(branch)
                }
            }
        }
    }
}

@Composable
fun BranchItem(branch: Doctor) {
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
                text = "${branch.firstName} ${branch.lastName}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = branch.location?.city ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = branch.location?.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DropdownMenuComponent(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                IconButtonComponent(
                    icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    onClick = { expanded = !expanded }
                )
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
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

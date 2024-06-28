package com.healthcare.mymolina.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.healthcare.core.AuthManager
import com.healthcare.mymolina.ui.branch.branch.BranchLocatorScreen
import com.healthcare.mymolina.ui.chat.ChatScreen
import com.healthcare.mymolina.ui.contactus.ContactUsScreen
import com.healthcare.mymolina.ui.initial.LoginScreen
import com.healthcare.mymolina.ui.initial.RegisterScreen
import com.healthcare.mymolina.ui.main.MainScreen
import com.healthcare.mymolina.ui.physician.PhysicianScreen
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import com.healthcare.mymolina.ui.urgentcare.UrgentCare
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Static object to hold a reference to Context
    companion object LeakySingleton {
        var context: Context? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            MyMolinaTheme {
                NavHost(navController = navController, startDestination = "LoginScreen") {
                    composable("MainScreen") { MainScreen(navController, Modifier) }
                    composable("LoginScreen") { LoginScreen(navController) }
                    composable("RegisterScreen") { RegisterScreen(navController) }
                    composable("UrgentCare") { UrgentCare(navController) }
                    composable("ContactUs") { ContactUsScreen(navController) }
                    composable("PhysicianScreen") { PhysicianScreen(navController) }
                    composable("BranchLocatorScreen") { BranchLocatorScreen(navController) }
                    composable("ChatScreen") { ChatScreen(navController) }
                }

                if (AuthManager.getCurrentUser() != null) {
                    navController.navigate("MainScreen")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBack(navController: NavController, title: String) {

    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
            }

        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677),
            titleContentColor = Color.White
        ),
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyMolinaTheme {
        MainScreen(rememberNavController(), Modifier)
    }
}

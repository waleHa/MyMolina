package com.healthcare.mymolina.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMolinaTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "LoginScreen" ){
                    composable("MainScreen"){MainScreen(navController,Modifier)}
                    composable("LoginScreen"){LoginScreen(navController)}
                    composable("RegisterScreen"){RegisterScreen(navController)}
                    composable("UrgentCare"){ UrgentCare(navController) }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyMolinaTheme {
        MainScreen(rememberNavController(),Modifier)
    }
}

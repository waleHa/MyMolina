package com.healthcare.mymolina.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.core.AuthManager
import com.healthcare.mymolina.R
import com.healthcare.mymolina.ui.NavScreens
import com.healthcare.mymolina.ui.physician.PhysicianViewModel
import com.healthcare.mymolina.ui.theme.MyMolinaTheme
import com.healthcare.mymolina.ui.component.*

data class GridItemData(
    val title: String,
    val iconResId: Int
)

val gridItems = listOf(
    GridItemData("Nurse Advice Line", R.drawable.icon_nurse),
    GridItemData("Find a Doctor", R.drawable.icon_doctor),
    GridItemData("Find a Pharmacy", R.drawable.icon_pharmacy),
    GridItemData("Find Urgent Care", R.drawable.icon_urgent_care),
    GridItemData("Help Line", R.drawable.icon_support),
    GridItemData("Contact Us", R.drawable.icon_telephone)
)

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier,
    viewModel: PhysicianViewModel = hiltViewModel()
) {
    val state by viewModel.physicianListSuccess.collectAsState()

    Log.i("TAG: MainScreen", state.toString())
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { TopBarItem(modifier) },
    ) {
        val backgroundColor: Color =
            if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677)

        Column {
            GridView(
                navController = navController,
                modifier = modifier
                    .padding(it)
                    .padding(bottom = 16.dp),
                textColor = Color.Black,
                gridItems = gridItems
            )
            ButtonComponent(backgroundColor, "SIGN OUT", {
                AuthManager.signOut()
                navController.popBackStack()
//                navigate(NavScreens.LoginScreen.route)
            }, modifier = modifier)
            OutlinedButtonComponent(backgroundColor, "NEW USER? REGISTER", {
                AuthManager.signOut()
                navController.navigate(NavScreens.RegisterScreen.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }, modifier)
        }

    }
}

@Composable
fun TopBarItem(modifier: Modifier) {
    val backgroundColor: Color =
        if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_molina),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(horizontal = 32.dp, vertical = 42.dp)
        )
    }
}

@Composable
fun GridView(
    navController: NavController,
    gridItems: List<GridItemData>,
    textColor: Color,
    modifier: Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(gridItems.size) { index ->
            val item = gridItems[index]
            GridItem(
                navController = navController,
                text = item.title,
                icon = painterResource(id = item.iconResId),
                textColor = textColor,
                modifier = Modifier
            )
        }
    }
}


@Composable
fun GridItem(
    navController: NavController,
    text: String,
    icon: Painter,
    textColor: Color,
    modifier: Modifier
) {
    TextButton(
        onClick = {
            if (text == "Find Urgent Care") {
                navController.navigate(NavScreens.UrgentCare.route)
            }
            if (text == "Nurse Advice Line") {
                navController.navigate(NavScreens.NurseAdviceLine.route)
            }

            if (text == "Find a Doctor") {
                navController.navigate(NavScreens.PhysicianScreen.route)
            }

            if (text == "Find a Pharmacy") {
                navController.navigate(NavScreens.BranchLocatorScreen.route)
            }

            if (text == "Help Line") {
                navController.navigate(NavScreens.ChatScreen.route)
            }

            if (text == "Contact Us") {
                navController.navigate(NavScreens.ContactUs.route)
            }
        },
        modifier = modifier
            .size(140.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = icon,
                contentDescription = text,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    MyMolinaTheme {
        MainScreen(rememberNavController(), Modifier)
    }
}
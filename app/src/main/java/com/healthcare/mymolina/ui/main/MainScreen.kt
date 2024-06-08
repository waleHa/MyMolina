package com.healthcare.mymolina.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthcare.mymolina.R
import com.healthcare.mymolina.ui.theme.MyMolinaTheme

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(modifier: Modifier) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { TopBarItem(modifier) },
    ) {
        val backgroundColor: Color =
            if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677)

        Column {
            GridView(
                modifier = modifier
                    .padding(it)
                    .padding(bottom = 16.dp),
                textColor = Color.Black,
                gridItems = gridItems
            )
            ButtonComponent(backgroundColor, "SIGN IN", {}, modifier)
            OutlinedButtonComponent(backgroundColor, "NEW USER? REGISTER", {}, modifier)
        }

    }
}

@Composable
private fun OutlinedButtonComponent(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xFF00796B)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = backgroundColor),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()

    ) {
        Text(text = text)
    }
}

@Composable
private fun ButtonComponent(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = text, color = Color.White)
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
                text = item.title,
                icon = painterResource(id = item.iconResId),
                textColor = textColor,
                modifier = Modifier
            )
        }
    }
}


@Composable
fun GridItem(text: String, icon: Painter, textColor: Color, modifier: Modifier) {
    TextButton(
        onClick = { /*TODO*/ },
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
        MainScreen(Modifier)
    }
}
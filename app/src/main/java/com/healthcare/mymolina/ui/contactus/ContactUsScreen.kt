package com.healthcare.mymolina.ui.contactus

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.mymolina.ui.component.*
import com.healthcare.mymolina.ui.theme.MyMolinaTheme

@Composable
fun ContactUsScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        if (!isSystemInDarkTheme()) Color(0xFF009493) else Color(0xFF006677),
                        if (!isSystemInDarkTheme()) Color(0xFF005577) else Color(0xFF008493)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageComponent(
            imageUrl = "https://www.example.com/molinalogo.png",
            contentDescription = "Molina Healthcare Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

        TextComponent(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = Color.White
        )

        SpacerComponent(height = 24.dp)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextComponent(
                    text = "Molina Healthcare of Washington",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpacerComponent(height = 8.dp)
                TextComponent(
                    text = "21540 30th Dr SE #400, Bothell, WA 98021",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpacerComponent(height = 8.dp)
                TextComponent(
                    text = "Phone: (800) 869-7165",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpacerComponent(height = 8.dp)
                TextComponent(
                    text = "Email: info@molinahealthcare.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SpacerComponent(height = 8.dp)
                TextComponent(
                    text = "Website: www.molinahealthcare.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                SpacerComponent(height = 16.dp)

                ButtonComponent(
                    backgroundColor = if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677),
                    text = "Visit Website",
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.molinahealthcare.com"))
                        context.startActivity(browserIntent)
                    },
                    modifier = Modifier.padding(8.dp)
                )

                ButtonComponent(
                    backgroundColor = if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677),
                    text = "Call Us",
                    onClick = {
                        val callIntent = Intent(Intent.ACTION_DIAL)
                        callIntent.data = Uri.parse("tel:8008697165")
                        context.startActivity(callIntent)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewContactUs() {
    MyMolinaTheme {
        ContactUsScreen(navController = rememberNavController())
    }
}
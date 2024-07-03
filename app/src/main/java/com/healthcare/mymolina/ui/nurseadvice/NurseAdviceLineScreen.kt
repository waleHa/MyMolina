package com.healthcare.mymolina.ui.nurseadvice

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healthcare.mymolina.ui.TopAppBarWithBack
import com.healthcare.mymolina.ui.component.ButtonIComponent
import com.healthcare.mymolina.ui.component.SpacerComponent
import com.healthcare.mymolina.ui.component.TextComponent
import com.healthcare.mymolina.ui.theme.DarkGreen
import kotlinx.coroutines.launch


@Composable
fun NurseAdviceLineScreen(navController: NavController, title: String = "Nurse Advice Line") {
    val context = LocalContext.current
    val viewModel: NurseAdviceLineViewModel = hiltViewModel()
    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            if (navController.currentBackStackEntry?.destination?.route !in listOf("LoginScreen", "RegisterScreen")) {
                TopAppBarWithBack(navController = navController, title = title)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                SwipeableButton(
                    text = "For emergency, swipe to call 911",
                    onSwipe = {
                        val callIntent = Intent(Intent.ACTION_DIAL)
                        callIntent.data = Uri.parse("tel:911")
                        context.startActivity(callIntent)
                    }
                )

                SpacerComponent(height = 16.dp)

                SwipeableButton(
                    text = "For Confidential 24/7 Suicide Or Mental Health Crisis Support, swipe to call 988",
                    onSwipe = {
                        val callIntent = Intent(Intent.ACTION_DIAL)
                        callIntent.data = Uri.parse("tel:988")
                        context.startActivity(callIntent)
                    }
                )

                SpacerComponent(height = 16.dp)

                TextComponent(
                    text = "988 is the National Suicide Lifeline. If you need suicide or mental health crisis support (or you are worried about someone else) then you can receive free and confidential support 24 hours, 7 days per week.",
                    modifier = Modifier.padding(8.dp)
                )

                SpacerComponent(height = 16.dp)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextComponent(
                            text = "24-Hour Nurse Advice Line",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )

                        TextComponent(
                            text = "Highly trained nurses are available 24 hours a day, 7 days a week. They can speak with you in your language. They will help you get the care you need.",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        ButtonIComponent(
                            backgroundColor = Color(0xFF00796B),
                            text = "Call 1-844-563-8045",
                            onClick = {
                                val callIntent = Intent(Intent.ACTION_DIAL)
                                callIntent.data = Uri.parse("tel:1-844-563-8045")
                                context.startActivity(callIntent)
                            },
                            modifier = Modifier.padding(vertical = 8.dp),
                            leadingIcon = Icons.Default.Call
                        )

                        ButtonIComponent(
                            backgroundColor = Color(0xFF00796B),
                            text = "Call 711",
                            onClick = {
                                val callIntent = Intent(Intent.ACTION_DIAL)
                                callIntent.data = Uri.parse("tel:711")
                                context.startActivity(callIntent)
                            },
                            modifier = Modifier.padding(vertical = 8.dp),
                            leadingIcon = Icons.Default.Call
                        )
                    }
                }

                SpacerComponent(height = 16.dp)

                TextComponent(
                    text = "When should you go to an Urgent Care Center (UCC)?",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                TextComponent(
                    text = "Use a UCC for a non-life threatening condition, such as:\n• Sore throat, cough or runny nose\n• Muscle strains/sprains\n• Minor cuts/burns\n• Earache\n• Rashes\n• Fever\n• General wound care\n• Urinary tract infection\n• Mild asthma\n• Flu screening",
                    modifier = Modifier.padding(8.dp)
                )

                SpacerComponent(height = 16.dp)

                TextComponent(
                    text = "Common Questions that Nurse can Answer:",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                TextComponent(
                    text = "• What should I do if I have a fever?\n• How do I manage my symptoms?\n• When should I see a doctor?\n• What over-the-counter medications are safe to take?",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun SwipeableButton(text: String, onSwipe: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 300) // Adjust the duration as needed
    )
    val progress = (animatedOffsetX / 1000f).coerceIn(0f, 1f)
    val backgroundColor = DarkGreen

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        backgroundColor,
                        backgroundColor.copy(alpha = 1f - progress)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        offsetX = 0f
                    },
                    onDragEnd = {
                        if (offsetX > size.width * 0.5) {
                            coroutineScope.launch {
                                onSwipe()
                            }
                        }
                        offsetX = 0f
                    },
                    onDragCancel = {
                        offsetX = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX = (offsetX + dragAmount).coerceIn(0f, size.width.toFloat())
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .absoluteOffset(x = animatedOffsetX.dp) // Offset the content
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

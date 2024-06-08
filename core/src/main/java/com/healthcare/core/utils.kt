package com.healthcare.core


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



fun String.toReadableDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return inputFormat.parse(this)?.let { outputFormat.format(it) } ?: this
}

fun Int.toReadableDate(): String {
    val date = Date(this.toLong() * 1000)
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return format.format(date)
}

// Non-composable function to get the painter and color filter

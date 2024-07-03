package com.healthcare.mymolina.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.healthcare.mymolina.R
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction




@Composable
fun ImageComponent(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.ic_molina2),
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}



@Composable
fun TextComponent(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677),
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}



@Composable
fun SpacerComponent(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedTextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = leadingIcon?.let { { Icon(imageVector = it, contentDescription = null) } },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        singleLine = true, // Prevent new lines
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Set IME action to 'Done'
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide() // Close the keyboard when 'Done' is pressed
            }
        ),
        modifier = modifier
    )
}


@Composable
fun OutlinedButtonComponent(
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
fun ButtonComponent(
    backgroundColor: Color = if (!isSystemInDarkTheme()) Color(0xFF008493) else Color(0xFF006677),
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
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
fun ButtonIComponent(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        leadingIcon?.let {
            Icon(it, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        }
        Text(text = text, color = Color.White)
    }
}

@Composable
fun IconButtonComponent(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
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
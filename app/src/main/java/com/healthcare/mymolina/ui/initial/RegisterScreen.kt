package com.healthcare.mymolina.ui.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.core.AuthManager
import com.healthcare.mymolina.ui.component.ImageComponent
import com.healthcare.mymolina.ui.component.*
import com.healthcare.mymolina.ui.theme.MyMolinaTheme

@Composable
fun RegisterScreen(navController: NavController) {

    val (firstName, setFirstName) = remember { mutableStateOf("") }
    val (lastName, setLastName) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    val (termsAccepted, setTermsAccepted) = remember { mutableStateOf(false) }
    val (registrationError, setRegistrationError) = remember { mutableStateOf<String?>(null) }

    val isFormValid =
        firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && termsAccepted

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageComponent(
            imageUrl = "https://hasdic.org/wp-content/uploads/sites/4/2022/03/Molina-Healthcare-Logo-Stacked.png",
            contentDescription = "",
            modifier = Modifier
                .size(250.dp)
                .padding(vertical = 16.dp)
        )
        Text("Hey there,", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Create an Account",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        SpacerComponent(height = 24.dp)

        OutlinedTextFieldComponent(
            value = firstName,
            onValueChange = setFirstName,
            label = "First Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        SpacerComponent(height = 8.dp)

        OutlinedTextFieldComponent(
            value = lastName,
            onValueChange = setLastName,
            label = "Last Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        SpacerComponent(height = 8.dp)

        OutlinedTextFieldComponent(
            value = email,
            onValueChange = setEmail,
            label = "Email",
            leadingIcon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth()
        )
        SpacerComponent(height = 8.dp)

        OutlinedTextFieldComponent(
            value = password,
            onValueChange = setPassword,
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            trailingIcon = {
                IconButtonComponent(
                    icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Toggle Password Visibility",
                    onClick = { setPasswordVisible(!passwordVisible) }
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        SpacerComponent(height = 16.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = termsAccepted, onCheckedChange = setTermsAccepted)
            Text("By continuing you accept our ", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = { /* Handle privacy policy click */ }) { Text("Privacy Policy & Terms") }
        }
        SpacerComponent(height = 12.dp)

        registrationError?.let {
            TextComponent(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier,
                style = MaterialTheme.typography.bodySmall
            )
        }
        SpacerComponent(height = 12.dp)

        ButtonComponent(
            onClick = {
                handleRegistration(
                    email = email,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    navController = navController,
                    setRegistrationError = setRegistrationError
                )
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
            text = "Register"
        )

        SpacerComponent(height = 16.dp)

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Already have an account? Login")
        }
    }
}


private fun handleRegistration(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    navController: NavController,
    setRegistrationError: (String?) -> Unit
) {
    if (email.isNotBlank() && password.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank()) {
        AuthManager.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("LoginScreen")
                } else {
                    setRegistrationError(task.exception?.message)
                }
            }
    } else {
        setRegistrationError("All fields must be filled and terms accepted.")
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    MyMolinaTheme {
        RegisterScreen(navController = rememberNavController())
    }
}

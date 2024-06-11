package com.healthcare.mymolina.ui.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.healthcare.core.AuthManager
import com.healthcare.mymolina.ui.component.ImageComponent
import com.healthcare.mymolina.ui.component.*
import com.healthcare.mymolina.ui.theme.MyMolinaTheme

@Composable
fun LoginScreen(navController: NavController, message: String = "Welcome to Molina App") {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    val (loginError, setLoginError) = remember { mutableStateOf<String?>(null) }

    val isFormValid = email.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageComponent(
            imageUrl = "https://hasdic.org/wp-content/uploads/sites/4/2022/03/Molina-Healthcare-Logo-Stacked.png",
            contentDescription = "",
            modifier = Modifier
                .size(250.dp)
                .padding(vertical = 16.dp)
        )
        Text(
            "Hey there,",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Text(
            message,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        SpacerComponent(height = 22.dp)

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
                    icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    onClick = { setPasswordVisible(!passwordVisible) }
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().imePadding(),
        )
        SpacerComponent(height = 16.dp)

        loginError?.let {
            TextComponent(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier,
                style = MaterialTheme.typography.bodySmall
            )
        }
        SpacerComponent(height = 16.dp)

        TextButton(onClick = {}) {
            Text(text = "Forgot your password?")
        }
        SpacerComponent(height = 24.dp)

        Button(
            onClick = {
                isVerified(email, password, navController, setLoginError)
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        SpacerComponent(height = 16.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text("Or", modifier = Modifier.padding(8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f))
        }
        SpacerComponent(height = 16.dp)

        TextButton(onClick = { navController.navigate("RegisterScreen") }) {
            Text("Don't have an account yet? Register")
        }
    }
}

private fun isVerified(
    email: String,
    password: String,
    navController: NavController,
    setLoginError: (String?) -> Unit
) {
    if (email.isNotBlank() && password.isNotBlank()) {
        AuthManager.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("MainScreen")
                } else {
                    setLoginError(task.exception?.message)
                }
            }
    } else {
        setLoginError("Email and Password cannot be empty")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyMolinaTheme {
        LoginScreen(navController = rememberNavController())
    }
}

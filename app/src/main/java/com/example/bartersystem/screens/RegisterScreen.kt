package com.example.bartersystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.Routes
import com.example.bartersystem.ui.theme.*

private val skillOptions = listOf(
    "Carpenter", "Plumber", "Electrician", "Mason",
    "Painter", "Welder", "Mechanic", "Farmer",
    "Tailor", "Cook", "Other"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var name                  by remember { mutableStateOf("") }
    var email                 by remember { mutableStateOf("") }
    var password              by remember { mutableStateOf("") }
    var selectedSkill         by remember { mutableStateOf("") }
    var skillDropdownExpanded by remember { mutableStateOf(false) }
    var passwordVisible       by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BrandCream, SurfaceLight)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Join BarterSystem",
                style = MaterialTheme.typography.headlineLarge,
                color = BrandBrown,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Create your skill profile and start trading",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 6.dp, bottom = 28.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Full Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = BrandOrange)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = BrandOrange,
                            unfocusedBorderColor = DividerColor,
                            focusedLabelColor    = BrandOrange
                        ),
                        singleLine = true
                    )

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = BrandOrange)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = BrandOrange,
                            unfocusedBorderColor = DividerColor,
                            focusedLabelColor    = BrandOrange
                        ),
                        singleLine = true
                    )

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = BrandOrange)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null, tint = TextSecondary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = BrandOrange,
                            unfocusedBorderColor = DividerColor,
                            focusedLabelColor    = BrandOrange
                        ),
                        singleLine = true
                    )

                    // Skill Dropdown
                    ExposedDropdownMenuBox(
                        expanded = skillDropdownExpanded,
                        onExpandedChange = { skillDropdownExpanded = !skillDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedSkill,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Your Primary Skill") },
                            leadingIcon = {
                                Icon(Icons.Default.Build, contentDescription = null, tint = BrandOrange)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = skillDropdownExpanded)
                            },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = BrandOrange,
                                unfocusedBorderColor = DividerColor,
                                focusedLabelColor    = BrandOrange
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = skillDropdownExpanded,
                            onDismissRequest = { skillDropdownExpanded = false }
                        ) {
                            skillOptions.forEach { skill ->
                                DropdownMenuItem(
                                    text = { Text(skill) },
                                    onClick = {
                                        selectedSkill         = skill
                                        skillDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && email.contains("@") &&
                                password.length >= 6 && selectedSkill.isNotBlank()
                            ) {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                    ) {
                        Text("Create Account", fontSize = 16.sp, color = BrandCream, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account? ", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Login", color = BrandOrange, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
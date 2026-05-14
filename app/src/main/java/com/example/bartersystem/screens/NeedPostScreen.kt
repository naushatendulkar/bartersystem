package com.example.bartersystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.Routes
import com.example.bartersystem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeedPostScreen(navController: NavController) {
    var title          by remember { mutableStateOf("") }
    var description    by remember { mutableStateOf("") }
    var skillRequired  by remember { mutableStateOf("") }
    var hoursEstimate  by remember { mutableStateOf("") }
    var offerSkill     by remember { mutableStateOf("") }
    var skillDropdown  by remember { mutableStateOf(false) }
    var offerDropdown  by remember { mutableStateOf(false) }
    var showSuccess    by remember { mutableStateOf(false) }

    val skillList = listOf("Carpenter", "Plumber", "Electrician", "Mason", "Painter", "Welder", "Mechanic", "Tailor", "Cook", "Other")

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = { showSuccess = false },
            confirmButton = {
                TextButton(onClick = {
                    showSuccess = false
                    navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                }) {
                    Text("View Board", color = BrandOrange, fontWeight = FontWeight.Bold)
                }
            },
            icon = { Text("✅", fontSize = 36.sp) },
            title = { Text("Post Published!", fontWeight = FontWeight.Bold, color = BrandBrown) },
            text  = { Text("Your need has been posted to the Skill Board. Community members will reach out soon!", color = TextSecondary) },
            containerColor = CardBackground
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post a Need", style = MaterialTheme.typography.titleLarge, color = BrandBrown, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrandBrown)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandCream)
            )
        },
        containerColor = SurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Info Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.horizontalGradient(listOf(BrandOrange.copy(alpha = 0.15f), BrandGreen.copy(alpha = 0.1f))))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("💡", fontSize = 24.sp)
                    Text(
                        "Describe what help you need and what skill you can offer in return. 1 Hour = 1 Skill Point.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BrandBrown
                    )
                }
            }

            // Post Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Post Title") },
                placeholder = { Text("e.g. Fix leaking roof pipe") },
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null, tint = BrandOrange) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange,
                    unfocusedContainerColor = CardBackground, focusedContainerColor = CardBackground
                ),
                singleLine = true
            )

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Describe the problem in detail...") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, tint = BrandOrange) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange,
                    unfocusedContainerColor = CardBackground, focusedContainerColor = CardBackground
                ),
                maxLines = 5
            )

            // Skill Required Dropdown
            ExposedDropdownMenuBox(expanded = skillDropdown, onExpandedChange = { skillDropdown = !skillDropdown }) {
                OutlinedTextField(
                    value = skillRequired,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Skill Required") },
                    leadingIcon = { Icon(Icons.Default.Build, contentDescription = null, tint = BrandOrange) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = skillDropdown) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange,
                        unfocusedContainerColor = CardBackground, focusedContainerColor = CardBackground
                    )
                )
                ExposedDropdownMenu(expanded = skillDropdown, onDismissRequest = { skillDropdown = false }) {
                    skillList.forEach { skill ->
                        DropdownMenuItem(text = { Text(skill) }, onClick = { skillRequired = skill; skillDropdown = false })
                    }
                }
            }

            // Skill Offered Dropdown
            ExposedDropdownMenuBox(expanded = offerDropdown, onExpandedChange = { offerDropdown = !offerDropdown }) {
                OutlinedTextField(
                    value = offerSkill,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Skill I Can Offer") },
                    leadingIcon = { Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = BrandGreen) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = offerDropdown) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandGreen, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandGreen,
                        unfocusedContainerColor = CardBackground, focusedContainerColor = CardBackground
                    )
                )
                ExposedDropdownMenu(expanded = offerDropdown, onDismissRequest = { offerDropdown = false }) {
                    skillList.forEach { skill ->
                        DropdownMenuItem(text = { Text(skill) }, onClick = { offerSkill = skill; offerDropdown = false })
                    }
                }
            }

            // Hours Estimate
            OutlinedTextField(
                value = hoursEstimate,
                onValueChange = { hoursEstimate = it.filter { c -> c.isDigit() } },
                label = { Text("Estimated Hours Needed") },
                placeholder = { Text("e.g. 3") },
                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = BrandOrange) },
                suffix = { Text("hours", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange,
                    unfocusedContainerColor = CardBackground, focusedContainerColor = CardBackground
                ),
                singleLine = true
            )

            // Skill Points Preview
            if (hoursEstimate.isNotBlank()) {
                val pts = hoursEstimate.toIntOrNull() ?: 0
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(BrandGreen.copy(alpha = 0.1f))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(22.dp))
                        Text("This swap will earn $pts Skill Point${if (pts != 1) "s" else ""}", color = BrandGreen, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && skillRequired.isNotBlank()) {
                        showSuccess = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
            ) {
                Icon(Icons.Default.PostAdd, contentDescription = null, tint = BrandCream)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Publish to Skill Board", fontSize = 16.sp, color = BrandCream, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
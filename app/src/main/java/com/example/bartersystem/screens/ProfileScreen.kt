package com.example.bartersystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.Routes
import com.example.bartersystem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", style = MaterialTheme.typography.titleLarge, color = BrandBrown, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrandBrown)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandCream)
            )
        },
        bottomBar = { BarterBottomNav(navController) },
        containerColor = SurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(BrandOrange),
                contentAlignment = Alignment.Center
            ) {
                Text("R", color = BrandCream, fontSize = 42.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text("Ramu Yadav", style = MaterialTheme.typography.headlineMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
            Text("Expert Plumber", style = MaterialTheme.typography.bodyLarge, color = BrandOrange)
            Text("Rajasthan, India", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)

            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(label = "Trust Score", value = "87", icon = Icons.Default.Star, color = TrustGold)
                StatCard(label = "Skill Points", value = "145", icon = Icons.Default.EmojiEvents, color = BrandGreen)
                StatCard(label = "Swaps Done", value = "12", icon = Icons.Default.SwapHoriz, color = BrandOrange)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Skills Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("My Skills", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    listOf("Plumbing", "Pipe Fitting", "Water Motor Repair", "Leak Detection").forEach { skill ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(skill, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Swaps
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Recent Swaps", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    SwapHistoryItem("Fixed Suresh's motor wiring", "Received: Electrical repair", "✅ Confirmed", true)
                    HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 8.dp))
                    SwapHistoryItem("Repaired Arjun's pipes", "Received: Mason work", "✅ Confirmed", true)
                    HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 8.dp))
                    SwapHistoryItem("Helped Meena with roof pipe", "Received: Tailoring", "⏳ Pending", false)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logout Button
            OutlinedButton(
                onClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = ErrorRed)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", color = ErrorRed, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, color: androidx.compose.ui.graphics.Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, fontSize = 11.sp)
        }
    }
}

@Composable
fun SwapHistoryItem(title: String, subtitle: String, status: String, confirmed: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, color = TextPrimary, fontWeight = FontWeight.Medium)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
        Text(
            text = status,
            style = MaterialTheme.typography.bodyMedium,
            color = if (confirmed) BrandGreen else BrandOrange,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}
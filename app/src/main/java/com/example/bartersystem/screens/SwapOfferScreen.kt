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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.Routes
import com.example.bartersystem.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapOfferScreen(navController: NavController) {
    var offerMessage      by remember { mutableStateOf("") }
    var hoursOffered      by remember { mutableStateOf("") }
    var myConfirmed       by remember { mutableStateOf(false) }
    var theirConfirmed    by remember { mutableStateOf(false) }
    var swapSent          by remember { mutableStateOf(false) }

    val bothConfirmed = myConfirmed && theirConfirmed

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Swap Offer", style = MaterialTheme.typography.titleLarge, color = BrandBrown, fontWeight = FontWeight.Bold) },
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
            // Post Being Replied To
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = BrandOrange.copy(alpha = 0.08f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Replying to", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(BrandOrange),
                            contentAlignment = Alignment.Center
                        ) { Text("S", color = BrandCream, fontWeight = FontWeight.Bold, fontSize = 18.sp) }
                        Column {
                            Text("Suresh Kumar", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
                            Text("Motor wiring repair", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SkillTag(label = "Needs: Plumber", color = BrandGreen)
                        SkillTag(label = "2h = 2 pts", color = BrandOrangeDark)
                        SkillTag(label = "⭐ 92 Trust", color = TrustGold)
                    }
                }
            }

            // Offer Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Your Swap Offer", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = offerMessage,
                        onValueChange = { offerMessage = it },
                        label = { Text("What will you offer?") },
                        placeholder = { Text("e.g. I'll fix your motor wiring in exchange for pipe repair at my house") },
                        leadingIcon = { Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = BrandOrange) },
                        modifier = Modifier.fillMaxWidth().height(110.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange
                        ),
                        maxLines = 4
                    )

                    OutlinedTextField(
                        value = hoursOffered,
                        onValueChange = { hoursOffered = it.filter { c -> c.isDigit() } },
                        label = { Text("Hours You Will Work") },
                        leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = BrandOrange) },
                        suffix = { Text("hours", color = TextSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandOrange, unfocusedBorderColor = DividerColor, focusedLabelColor = BrandOrange
                        ),
                        singleLine = true
                    )

                    if (hoursOffered.isNotBlank()) {
                        val pts = hoursOffered.toIntOrNull() ?: 0
                        Box(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                                .background(BrandGreen.copy(alpha = 0.1f)).padding(12.dp)
                        ) {
                            Text("You will earn $pts Skill Point${if (pts != 1) "s" else ""} upon swap confirmation",
                                color = BrandGreen, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    if (!swapSent) {
                        Button(
                            onClick = { if (offerMessage.isNotBlank()) swapSent = true },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = BrandCream)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Send Swap Offer", color = BrandCream, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp))
                                .background(BrandGreen.copy(alpha = 0.12f)).padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandGreen)
                                Text("Offer Sent! Waiting for confirmation.", color = BrandGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Confirmation Section — Trust Score logic
            if (swapSent) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Swap Confirmation", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
                        Text(
                            "Trust Score increases only after BOTH parties confirm the swap is complete.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )

                        HorizontalDivider(color = DividerColor)

                        // My Confirmation
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(BrandOrange), contentAlignment = Alignment.Center) {
                                    Text("R", color = BrandCream, fontWeight = FontWeight.Bold)
                                }
                                Column {
                                    Text("You (Ramu)", style = MaterialTheme.typography.bodyLarge, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                                    Text("Mark swap as done", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                }
                            }
                            Checkbox(
                                checked = myConfirmed,
                                onCheckedChange = { myConfirmed = it },
                                colors = CheckboxDefaults.colors(checkedColor = BrandGreen, uncheckedColor = DividerColor)
                            )
                        }

                        // Their Confirmation (simulated)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(BrandGreen), contentAlignment = Alignment.Center) {
                                    Text("S", color = BrandCream, fontWeight = FontWeight.Bold)
                                }
                                Column {
                                    Text("Suresh Kumar", style = MaterialTheme.typography.bodyLarge, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                                    Text("Their confirmation", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                }
                            }
                            Checkbox(
                                checked = theirConfirmed,
                                onCheckedChange = { theirConfirmed = it },
                                colors = CheckboxDefaults.colors(checkedColor = BrandGreen, uncheckedColor = DividerColor)
                            )
                        }

                        // Result Banner
                        if (bothConfirmed) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Brush.horizontalGradient(listOf(BrandGreen.copy(alpha = 0.2f), TrustGold.copy(alpha = 0.15f))))
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("🎉", fontSize = 36.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Swap Confirmed!", style = MaterialTheme.typography.titleMedium, color = BrandGreen, fontWeight = FontWeight.Bold)
                                    Text("+2 Skill Points & +5 Trust Score added to your profile", style = MaterialTheme.typography.bodyMedium, color = TextSecondary, textAlign = TextAlign.Center)
                                }
                            }

                            Button(
                                onClick = { navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } } },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                            ) {
                                Text("Back to Skill Board", color = BrandCream, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
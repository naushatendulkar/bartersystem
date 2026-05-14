package com.example.bartersystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.Routes
import com.example.bartersystem.ui.theme.*

data class NeedPost(
    val id: Int,
    val userName: String,
    val userSkill: String,
    val title: String,
    val description: String,
    val skillRequired: String,
    val hoursRequired: Int,
    val trustScore: Int,
    val skillPoints: Int
)

val samplePosts = listOf(
    NeedPost(1, "Ramu Yadav",   "Plumber",      "Fix leaking roof pipe",       "Water pipe near my roof has been leaking for 2 days. Need urgent help.",         "Carpenter",     3, 87, 45),
    NeedPost(2, "Suresh Kumar", "Electrician",  "Motor wiring repair",         "My water motor stopped working. I'll give plumbing help in exchange.",            "Plumber",       2, 92, 60),
    NeedPost(3, "Meena Devi",   "Tailor",       "Repaint kitchen walls",       "Kitchen needs fresh coat of paint. I'll stitch clothes in return.",               "Painter",       4, 78, 30),
    NeedPost(4, "Arjun Singh",  "Mason",        "Fix electrical switchboard",  "Two switches are broken and need replacement. Mason work offered in exchange.",   "Electrician",   1, 95, 75),
    NeedPost(5, "Lakshmi Bai",  "Cook",         "Build a small storage shelf", "Need a wooden shelf in my kitchen. Will cook meals in exchange.",                 "Carpenter",     2, 83, 40),
    NeedPost(6, "Vijay Patel",  "Mechanic",     "Repair roof tiles",           "Three roof tiles cracked after rain. Engine repair offered for help.",            "Mason",         3, 88, 55)
)

val filterSkills = listOf("All", "Carpenter", "Plumber", "Electrician", "Mason", "Painter", "Tailor", "Cook", "Mechanic")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery    by remember { mutableStateOf("") }

    val filteredPosts = samplePosts.filter { post ->
        val matchesFilter = selectedFilter == "All" || post.skillRequired == selectedFilter
        val matchesSearch = searchQuery.isBlank() ||
                post.title.contains(searchQuery, ignoreCase = true) ||
                post.description.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesSearch
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("BarterSystem", style = MaterialTheme.typography.titleLarge, color = BrandBrown, fontWeight = FontWeight.Bold)
                        Text("Skill Board", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.PROFILE) }) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(BrandOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("R", color = BrandCream, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandCream)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.NEED_POST) },
                containerColor = BrandOrange,
                contentColor = BrandCream,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Post Need", fontWeight = FontWeight.Bold)
                }
            }
        },
        bottomBar = { BarterBottomNav(navController) },
        containerColor = SurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search skills or needs...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BrandOrange) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = BrandOrange,
                    unfocusedBorderColor = DividerColor,
                    unfocusedContainerColor = CardBackground,
                    focusedContainerColor   = CardBackground
                ),
                singleLine = true
            )

            // Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                items(filterSkills) { skill ->
                    FilterChip(
                        selected = selectedFilter == skill,
                        onClick  = { selectedFilter = skill },
                        label    = { Text(skill) },
                        colors   = FilterChipDefaults.filterChipColors(
                            selectedContainerColor     = BrandOrange,
                            selectedLabelColor         = BrandCream,
                            containerColor             = CardBackground,
                            labelColor                 = TextSecondary
                        )
                    )
                }
            }

            // Posts Count
            Text(
                text = "${filteredPosts.size} posts found",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Posts List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPosts) { post ->
                    NeedPostCard(post = post, onClick = { navController.navigate(Routes.SWAP_OFFER) })
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun NeedPostCard(post: NeedPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(BrandOrange.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.userName.first().toString(),
                        color = BrandOrange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(post.userName, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Text("Expert ${post.userSkill}", style = MaterialTheme.typography.bodyMedium, color = BrandOrange)
                }
                // Trust Score Badge
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⭐", fontSize = 14.sp)
                    Text("${post.trustScore}", style = MaterialTheme.typography.labelLarge, color = BrandBrown, fontWeight = FontWeight.Bold)
                    Text("Trust", style = MaterialTheme.typography.bodyMedium, color = TextSecondary, fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(post.title, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(post.description, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, maxLines = 2)

            Spacer(modifier = Modifier.height(12.dp))

            // Tags Row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SkillTag(label = "Needs: ${post.skillRequired}", color = BrandGreen)
                SkillTag(label = "${post.hoursRequired}h = ${post.hoursRequired} pts", color = BrandOrangeDark)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandOrange)
            ) {
                Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = BrandCream)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Make Swap Offer", color = BrandCream, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SkillTag(label: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = color, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    }
}

@Composable
fun BarterBottomNav(navController: NavController) {
    NavigationBar(containerColor = BrandCream) {
        NavigationBarItem(
            selected = true,
            onClick  = { navController.navigate(Routes.HOME) },
            icon     = { Icon(Icons.Default.Home, contentDescription = null) },
            label    = { Text("Board") },
            colors   = NavigationBarItemDefaults.colors(indicatorColor = BrandOrange.copy(alpha = 0.2f), selectedIconColor = BrandOrange, selectedTextColor = BrandOrange)
        )
        NavigationBarItem(
            selected = false,
            onClick  = { navController.navigate(Routes.NEED_POST) },
            icon     = { Icon(Icons.Default.PostAdd, contentDescription = null) },
            label    = { Text("Post") },
            colors   = NavigationBarItemDefaults.colors(indicatorColor = BrandOrange.copy(alpha = 0.2f), selectedIconColor = BrandOrange, selectedTextColor = BrandOrange)
        )
        NavigationBarItem(
            selected = false,
            onClick  = { navController.navigate(Routes.CHAT) },
            icon     = { Icon(Icons.Default.Chat, contentDescription = null) },
            label    = { Text("Chats") },
            colors   = NavigationBarItemDefaults.colors(indicatorColor = BrandOrange.copy(alpha = 0.2f), selectedIconColor = BrandOrange, selectedTextColor = BrandOrange)
        )
        NavigationBarItem(
            selected = false,
            onClick  = { navController.navigate(Routes.PROFILE) },
            icon     = { Icon(Icons.Default.Person, contentDescription = null) },
            label    = { Text("Profile") },
            colors   = NavigationBarItemDefaults.colors(indicatorColor = BrandOrange.copy(alpha = 0.2f), selectedIconColor = BrandOrange, selectedTextColor = BrandOrange)
        )
    }
}
package com.example.bartersystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bartersystem.ui.theme.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: Int,
    val text: String,
    val isMe: Boolean,
    val time: String
)

val initialMessages = listOf(
    ChatMessage(1, "Hello! I saw your post about motor wiring repair. I'm a plumber and can help.", false, "9:10 AM"),
    ChatMessage(2, "Hi Ramu! Yes, the motor has been faulty for a week. Can you fix it tomorrow?", true, "9:12 AM"),
    ChatMessage(3, "Sure! I'll come in the morning. In exchange, I need help with my roof pipe leak.", false, "9:13 AM"),
    ChatMessage(4, "That works! 2 hours of motor work vs 2 hours of pipe repair. 2 Skill Points each.", true, "9:15 AM"),
    ChatMessage(5, "Deal! I'll send a formal Swap Offer now.", false, "9:16 AM")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    var messageText  by remember { mutableStateOf("") }
    val messages     = remember { mutableStateListOf(*initialMessages.toTypedArray()) }
    val listState    = rememberLazyListState()
    val coroutine    = rememberCoroutineScope()
    var msgIdCounter by remember { mutableStateOf(initialMessages.size + 1) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier.size(38.dp).clip(CircleShape).background(BrandGreen),
                            contentAlignment = Alignment.Center
                        ) { Text("S", color = BrandCream, fontWeight = FontWeight.Bold) }
                        Column {
                            Text("Suresh Kumar", style = MaterialTheme.typography.titleMedium, color = BrandBrown, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(BrandGreen))
                                Text("Online · Expert Electrician", style = MaterialTheme.typography.bodyMedium, color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrandBrown)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = BrandOrange)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrandCream)
            )
        },
        containerColor = SurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            // Swap Summary Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandOrange.copy(alpha = 0.1f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = BrandOrange, modifier = Modifier.size(18.dp))
                        Text("Motor Repair ↔ Pipe Fix · 2h each", style = MaterialTheme.typography.bodyMedium, color = BrandBrown, fontWeight = FontWeight.SemiBold)
                    }
                    Text("⭐ 92", style = MaterialTheme.typography.bodyMedium, color = TrustGold, fontWeight = FontWeight.Bold)
                }
            }

            // Messages
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(message = msg)
                }
            }

            // Input Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandCream)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandOrange,
                        unfocusedBorderColor = DividerColor,
                        unfocusedContainerColor = CardBackground,
                        focusedContainerColor = CardBackground
                    ),
                    maxLines = 3
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages.add(
                                ChatMessage(
                                    id   = msgIdCounter++,
                                    text = messageText,
                                    isMe = true,
                                    time = "Now"
                                )
                            )
                            messageText = ""
                            coroutine.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BrandOrange)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = BrandCream, modifier = Modifier.size(22.dp))
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isMe) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isMe) ChatBubbleMe else ChatBubbleOther
    val textColor   = if (message.isMe) BrandCream else TextPrimary
    val bubbleShape = if (message.isMe)
        RoundedCornerShape(18.dp, 18.dp, 4.dp, 18.dp)
    else
        RoundedCornerShape(18.dp, 18.dp, 18.dp, 4.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(message.text, color = textColor, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = message.time,
                    color = if (message.isMe) BrandCream.copy(alpha = 0.7f) else TextSecondary,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
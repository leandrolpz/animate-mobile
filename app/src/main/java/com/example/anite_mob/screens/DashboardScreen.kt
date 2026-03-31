package com.example.anite_mob.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anite_mob.ui.theme.PurplePrimary

// Modelo para cada traço no desenho
data class DrawingStroke(
    val path: Path,
    val color: Color,
    val width: Float,
    val isEraser: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    // Estados do Desenho
    val frames = remember { mutableStateListOf(mutableStateListOf<DrawingStroke>()) }
    var currentFrameIndex by remember { mutableIntStateOf(0) }
    
    var selectedColor by remember { mutableStateOf(Color.Black) }
    var brushSize by remember { mutableFloatStateOf(8f) }
    var isEraser by remember { mutableStateOf(false) }
    var onionSkinEnabled by remember { mutableStateOf(true) }
    
    // Para forçar recomposição durante o drag
    var tick by remember { mutableLongStateOf(0L) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AniMate Studio", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A1A))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF121212))
                    .padding(8.dp)
            ) {
                // Toolbar de Ferramentas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { isEraser = false }) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = if (!isEraser) PurplePrimary else Color.White)
                    }
                    IconButton(onClick = { isEraser = true }) {
                        Icon(Icons.Default.Clear, contentDescription = null, tint = if (isEraser) PurplePrimary else Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(selectedColor)
                            .border(1.dp, Color.White, CircleShape)
                            .clickable { 
                                selectedColor = if (selectedColor == Color.Black) Color.Red else Color.Black 
                            }
                    )
                    Slider(
                        value = brushSize,
                        onValueChange = { brushSize = it },
                        valueRange = 2f..50f,
                        modifier = Modifier.width(100.dp),
                        colors = SliderDefaults.colors(thumbColor = PurplePrimary, activeTrackColor = PurplePrimary)
                    )
                    IconButton(onClick = { if (frames[currentFrameIndex].isNotEmpty()) frames[currentFrameIndex].removeAt(frames[currentFrameIndex].size - 1) }) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                    }
                }

                // Timeline de Frames
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /* Play Logic */ }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Green)
                        }
                        Text("12 FPS", color = Color.Gray, fontSize = 12.sp)
                    }
                    
                    IconButton(onClick = { onionSkinEnabled = !onionSkinEnabled }) {
                        Icon(
                            Icons.Default.Build, 
                            contentDescription = "Onion Skin", 
                            tint = if (onionSkinEnabled) PurplePrimary else Color.Gray
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(frames) { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(60.dp, 40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (index == currentFrameIndex) PurplePrimary else Color.DarkGray)
                                .clickable { currentFrameIndex = index },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${index + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                    
                    item {
                        Box(
                            modifier = Modifier
                                .size(60.dp, 40.dp)
                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                                .clickable { 
                                    frames.add(mutableStateListOf())
                                    currentFrameIndex = frames.size - 1
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF2C2C2C))
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .pointerInput(currentFrameIndex, selectedColor, brushSize, isEraser) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val newPath = Path().apply { moveTo(offset.x, offset.y) }
                            frames[currentFrameIndex].add(
                                DrawingStroke(newPath, if (isEraser) Color.White else selectedColor, brushSize, isEraser)
                            )
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            frames[currentFrameIndex].lastOrNull()?.path?.lineTo(change.position.x, change.position.y)
                            tick++
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                tick.let {
                    // Onion Skin (Frame Anterior)
                    if (onionSkinEnabled && currentFrameIndex > 0) {
                        frames[currentFrameIndex - 1].forEach { stroke ->
                            drawPath(
                                path = stroke.path,
                                color = stroke.color.copy(alpha = 0.2f),
                                style = Stroke(width = stroke.width, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }

                    // Frame Atual
                    frames[currentFrameIndex].forEach { stroke ->
                        drawPath(
                            path = stroke.path,
                            color = stroke.color,
                            style = Stroke(
                                width = stroke.width,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
            }
        }
    }
}

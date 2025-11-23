package dev.surovtsev.gymmind.presentation.programs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.surovtsev.gymmind.core.theme.*
import dev.surovtsev.gymmind.domain.model.ProgramModel
import dev.surovtsev.gymmind.domain.model.enums.ProgramDifficulty
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramsScreen(
    viewModel: ProgramsViewModel = hiltViewModel()
) {
    val programs by viewModel.programs.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    AnimatedNeonBackground(
        intensity = 0.5f,
        enableGrid = true,
        enableRings = false,
        enableCornerDots = false
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Программы",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                )
            },
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Фильтры
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedFilter is ProgramFilter.All,
                            onClick = { viewModel.setFilter(ProgramFilter.All) },
                            label = { Text("Все") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedFilter is ProgramFilter.Featured,
                            onClick = { viewModel.setFilter(ProgramFilter.Featured) },
                            label = { Text("Избранные") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedFilter is ProgramFilter.Free,
                            onClick = { viewModel.setFilter(ProgramFilter.Free) },
                            label = { Text("Бесплатные") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedFilter is ProgramFilter.Premium,
                            onClick = { viewModel.setFilter(ProgramFilter.Premium) },
                            label = { Text("Премиум") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                // Список программ
                if (programs.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Программы не найдены",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondary
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(programs) { program ->
                            ProgramCard(
                                program = program,
                                onClick = {
                                    // TODO: Navigate to program details
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramCard(
    program: ProgramModel,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header с названием и премиум badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = program.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (program.shortDescription != null) {
                        Text(
                            text = program.shortDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Badges
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (program.isPremium) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = Accent.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Premium",
                                    modifier = Modifier.size(14.dp),
                                    tint = Accent
                                )
                                Text(
                                    text = "${program.price?.toInt()} ₽",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Accent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (program.isFeatured) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = Primary.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Featured",
                                    modifier = Modifier.size(14.dp),
                                    tint = Primary
                                )
                                Text(
                                    text = "Топ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Информационные чипы
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Длительность
                program.duration.weeks?.let { weeks ->
                    ProgramInfoChip(
                        icon = Icons.Default.CalendarMonth,
                        text = "$weeks ${getWeeksText(weeks)}"
                    )
                }

                // Тренировок в неделю
                program.duration.sessionsPerWeek?.let { sessions ->
                    ProgramInfoChip(
                        icon = Icons.Default.FitnessCenter,
                        text = "$sessions×/нед"
                    )
                }

                // Сложность
                ProgramInfoChip(
                    icon = Icons.Default.TrendingUp,
                    text = getDifficultyText(program.difficulty)
                )
            }

            // Рейтинг и статистика
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Рейтинг
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = String.format("%.1f", program.reviews.averageRating),
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Завершений
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completions",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${program.statistics.totalEnrollments} чел",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                    }
                }

                // Кнопка подробнее
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Details",
                    tint = Primary
                )
            }

            // Теги
            if (program.tags.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    program.tags.take(3).forEach { tag ->
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = Primary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = Primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramInfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = TextSecondary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}

private fun getWeeksText(weeks: Int): String {
    return when {
        weeks % 10 == 1 && weeks % 100 != 11 -> "неделя"
        weeks % 10 in 2..4 && (weeks % 100 < 10 || weeks % 100 >= 20) -> "недели"
        else -> "недель"
    }
}

private fun getDifficultyText(difficulty: ProgramDifficulty): String {
    return when (difficulty) {
        ProgramDifficulty.BEGINNER -> "Новичок"
        ProgramDifficulty.INTERMEDIATE -> "Средний"
        ProgramDifficulty.ADVANCED -> "Продвинутый"
        ProgramDifficulty.EXPERT -> "Эксперт"
        ProgramDifficulty.ADAPTIVE -> "Адаптивный"
    }
}

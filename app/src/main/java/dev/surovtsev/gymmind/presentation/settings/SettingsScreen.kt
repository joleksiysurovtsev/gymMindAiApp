package dev.surovtsev.gymmind.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.surovtsev.gymmind.core.theme.*
import dev.surovtsev.gymmind.presentation.components.AnimatedNeonBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isGuestMode by viewModel.isGuestMode.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    var showSignOutDialog by remember { mutableStateOf(false) }

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
                            text = "Настройки",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Профиль
                item {
                    SettingsSectionHeader(title = "Профиль")
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = if (isGuestMode) "Гостевой режим" else "Профиль",
                        subtitle = if (isGuestMode) "Войдите для полного доступа" else userEmail,
                        onClick = {
                            // TODO: Navigate to profile edit or sign in
                        }
                    )
                }

                if (!isGuestMode) {
                    item {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                        )
                    }
                }

                // Уведомления
                item {
                    SettingsSectionHeader(title = "Уведомления")
                }

                item {
                    SettingsSwitchItem(
                        icon = Icons.Default.Notifications,
                        title = "Напоминания о тренировках",
                        subtitle = "Получать уведомления о запланированных тренировках",
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications(it) }
                    )
                }

                item {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                    )
                }

                // Внешний вид
                item {
                    SettingsSectionHeader(title = "Внешний вид")
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Palette,
                        title = "Тема",
                        subtitle = "Темная (по умолчанию)",
                        onClick = {
                            // TODO: Theme selection
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Language,
                        title = "Язык",
                        subtitle = "Русский",
                        onClick = {
                            // TODO: Language selection
                        }
                    )
                }

                item {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                    )
                }

                // О приложении
                item {
                    SettingsSectionHeader(title = "О приложении")
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "Версия",
                        subtitle = "1.0.0",
                        onClick = {}
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.PrivacyTip,
                        title = "Политика конфиденциальности",
                        onClick = {
                            // TODO: Open privacy policy
                        }
                    )
                }

                item {
                    SettingsItem(
                        icon = Icons.Default.Description,
                        title = "Условия использования",
                        onClick = {
                            // TODO: Open terms
                        }
                    )
                }

                if (!isGuestMode) {
                    item {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                        )
                    }

                    // Аккаунт
                    item {
                        SettingsSectionHeader(title = "Аккаунт")
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.Logout,
                            title = "Выйти из аккаунта",
                            titleColor = Error,
                            onClick = {
                                showSignOutDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    // Sign Out Dialog
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = Error
                )
            },
            title = {
                Text(text = "Выход из аккаунта")
            },
            text = {
                Text(text = "Вы уверены, что хотите выйти из своего аккаунта?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSignOutDialog = false
                        viewModel.signOut()
                    }
                ) {
                    Text("Выйти", color = Error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = Primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    titleColor: androidx.compose.ui.graphics.Color = TextPrimary,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = titleColor.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = titleColor,
                        fontWeight = FontWeight.Medium
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TextPrimary.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = OnPrimary,
                    checkedTrackColor = Primary,
                    uncheckedThumbColor = TextSecondary,
                    uncheckedTrackColor = Divider
                )
            )
        }
    }
}

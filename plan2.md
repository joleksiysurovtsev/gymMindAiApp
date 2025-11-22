# GymMind AI - Пошаговый План Реализации По Экранам

**Дата**: 2025-11-22
**Подход**: Feature-by-Feature (по экранам в порядке user flow)
**Цель**: Создать GymMind AI с нуля, реализуя каждый экран полностью

---

## 📱 User Flow

```
Splash → Login → Onboarding → Home → [Programs/Workout/AI Coach/Profile]
```

---

## ⚙️ ШАГ 0: Базовая Инициализация Проекта (3-4 часа)

### 0.1: Создание Android Studio Проекта
**Время**: 30 мин

1. **File → New → New Project**
2. Выбрать **Empty Activity** (Compose)
3. Настройки:
   - Name: `GymMind AI`
   - Package: `dev.surovtsev.gymmind`
   - Language: Kotlin
   - Minimum SDK: API 26
   - Build: Kotlin DSL

---

### 0.2: Настройка Gradle
**Время**: 1-1.5 часа

#### `gradle/libs.versions.toml`

```toml
[versions]
agp = "8.13.1"
kotlin = "2.0.21"
ksp = "2.0.21-1.0.28"
googleServices = "4.4.4"
firebaseCrashlytics = "3.0.2"
coreKtx = "1.17.0"
composeBom = "2025.11.00"
hilt = "2.54"
room = "2.8.3"
retrofit = "2.9.0"
firebaseBom = "34.5.0"
coil = "2.5.0"
camera = "1.5.1"
datastore = "1.1.7"
coroutines = "1.9.0"
credentialManager = "1.5.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version = "1.11.0" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version = "2.9.6" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version = "2.9.4" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version = "1.3.0" }
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
retrofit-converter-scalars = { group = "com.squareup.retrofit2", name = "converter-scalars", version.ref = "retrofit" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version = "4.12.0" }
gson = { group = "com.google.code.gson", name = "gson", version = "2.13.2" }
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
firebase-firestore = { group = "com.google.firebase", name = "firebase-firestore" }
firebase-storage = { group = "com.google.firebase", name = "firebase-storage" }
firebase-analytics = { group = "com.google.firebase", name = "firebase-analytics" }
firebase-crashlytics = { group = "com.google.firebase", name = "firebase-crashlytics" }
dagger-hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
dagger-hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
androidx-credentials = { group = "androidx.credentials", name = "credentials", version.ref = "credentialManager" }
androidx-credentials-play-services-auth = { group = "androidx.credentials", name = "credentials-play-services-auth", version.ref = "credentialManager" }
play-services-auth = { group = "com.google.android.gms", name = "play-services-auth", version = "21.4.0" }
googleid = { group = "com.google.android.libraries.identity.googleid", name = "googleid", version = "1.1.1" }
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
mediapipe-tasks-vision = { group = "com.google.mediapipe", name = "tasks-vision", version = "0.20230731" }
androidx-camera-camera2 = { group = "androidx.camera", name = "camera-camera2", version.ref = "camera" }
androidx-camera-lifecycle = { group = "androidx.camera", name = "camera-lifecycle", version.ref = "camera" }
androidx-camera-video = { group = "androidx.camera", name = "camera-video", version.ref = "camera" }
androidx-camera-view = { group = "androidx.camera", name = "camera-view", version.ref = "camera" }
media3-exoplayer = { group = "androidx.media3", name = "media3-exoplayer", version = "1.8.0" }
media3-ui = { group = "androidx.media3", name = "media3-ui", version = "1.8.0" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
google-services = { id = "com.google.gms.google-services", version.ref = "googleServices" }
firebase-crashlytics = { id = "com.google.firebase.crashlytics", version.ref = "firebaseCrashlytics" }
```

#### `app/build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "dev.surovtsev.gymmind"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.surovtsev.gymmind"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BACKEND_URL", "\"http://10.0.2.2:3000/\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BACKEND_URL", "\"https://api.gymmind.ai/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // MultiDex
    implementation("androidx.multidex:multidex:2.0.1")

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
    implementation(libs.googleid)

    // Image Loading
    implementation(libs.coil.compose)

    // MediaPipe
    implementation(libs.mediapipe.tasks.vision)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)

    // ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
}
```

---

### 0.3: Создание Базовой Структуры
**Время**: 30 мин

Создать структуру пакетов в `app/src/main/java/dev/surovtsev/gymmind/`:

```
dev.surovtsev.gymmind/
├── core/
│   ├── di/
│   ├── navigation/
│   └── theme/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entity/
│   │   └── converters/
│   ├── remote/
│   └── repository/
├── domain/
│   ├── model/
│   └── repository/
└── presentation/
    ├── splash/
    ├── welcome/
    ├── login/
    ├── onboarding/
    ├── home/
    ├── workout/
    ├── programs/
    ├── coach/
    ├── profile/
    └── components/
```

---

### 0.4: Application Класс и Hilt
**Время**: 30 мин

#### `GymMindApplication.kt`

```kotlin
package dev.surovtsev.gymmind

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GymMindApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        // Инициализация будет добавлена позже
    }
}
```

#### `AndroidManifest.xml`

```xml
<manifest ...>
    <application
        android:name=".GymMindApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.GymMindAI">
        ...
    </application>
</manifest>
```

#### `core/di/AppModule.kt`

```kotlin
package dev.surovtsev.gymmind.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}
```

**Результат**: Базовый проект с Hilt настроен

---

## 🎨 ШАГ 1: Дизайн Система (3-4 часа)

### 1.1: Цвета
**Время**: 30 мин

#### `core/theme/Color.kt`

```kotlin
package dev.surovtsev.gymmind.core.theme

import androidx.compose.ui.graphics.Color

// Background
val BgPrimary = Color(0xFF0D1117)
val BgSurface = Color(0xFF1B1F24)
val BgSurfaceVariant = Color(0xFF22262E)
val BgElevated = Color(0xFF252A33)
val Divider = Color(0xFF2D333B)

// Primary
val Primary = Color(0xFF007BFF)
val PrimaryVariant = Color(0xFF0057CC)
val PrimaryBright = Color(0xFF00B8FF)
val OnPrimary = Color(0xFFFFFFFF)

// Accent
val Accent = Color(0xFF00E0FF)
val AccentAlt = Color(0xFF00FF88)

// System
val Success = Color(0xFF00FF88)
val Warning = Color(0xFFFACC15)
val Error = Color(0xFFFF4F5A)

// Text
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9CA3AF)
val TextTertiary = Color(0xFF6B7280)
val TextDisabled = Color(0xFF4B5563)
```

---

### 1.2: Типография (с шрифтом Inter)
**Время**: 1 час

**Скачать шрифты Inter** с Google Fonts и добавить в `app/src/main/res/font/`:
- `inter_regular.ttf`
- `inter_medium.ttf`
- `inter_semibold.ttf`
- `inter_bold.ttf`

#### `core/theme/Type.kt`

```kotlin
package dev.surovtsev.gymmind.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.surovtsev.gymmind.R

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val GymMindTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)
```

---

### 1.3: Формы и Тема
**Время**: 30 мин

#### `core/theme/Shape.kt`

```kotlin
package dev.surovtsev.gymmind.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val GymMindShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)
```

#### `core/theme/Theme.kt`

```kotlin
package dev.surovtsev.gymmind.core.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GymMindColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    secondary = Accent,
    tertiary = AccentAlt,
    background = BgPrimary,
    onBackground = TextPrimary,
    surface = BgSurface,
    onSurface = TextPrimary,
    surfaceVariant = BgSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = TextPrimary,
    outline = Divider
)

@Composable
fun GymMindTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GymMindColorScheme,
        typography = GymMindTypography,
        shapes = GymMindShapes,
        content = content
    )
}
```

---

### 1.4: Базовые UI Компоненты
**Время**: 1-1.5 часа

#### `presentation/components/Buttons.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.surovtsev.gymmind.core.theme.*

@Composable
fun GymMindButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary,
            disabledContainerColor = Divider,
            disabledContentColor = TextDisabled
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = OnPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Primary, PrimaryBright)
                )
            )
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = text.uppercase(),
            color = OnPrimary,
            style = MaterialTheme.typography.labelLarge,
            letterSpacing = 1.2.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
```

**Результат**: Дизайн система "Neon Focus" готова

---

## 🚀 ШАГ 2: Splash Screen (2-3 часа)

### 2.1: Navigation Routes
**Время**: 30 мин

#### `core/navigation/Routes.kt`

```kotlin
package dev.surovtsev.gymmind.core.navigation

object Routes {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val PROGRAMS = "programs"
    const val WORKOUT_EXECUTION = "workout_execution/{workoutId}"
    const val AI_COACH = "ai_coach"
    const val PROFILE = "profile"

    fun workoutExecution(workoutId: String) = "workout_execution/$workoutId"
}
```

---

### 2.2: DataStore для User Preferences
**Время**: 30 мин

#### `core/di/DataStoreModule.kt`

```kotlin
package dev.surovtsev.gymmind.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
```

#### `data/local/UserPreferences.kt`

```kotlin
package dev.surovtsev.gymmind.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { it[IS_LOGGED_IN] ?: false }
    val userId: Flow<String?> = dataStore.data.map { it[USER_ID] }
    val hasCompletedOnboarding: Flow<Boolean> = dataStore.data.map { it[HAS_COMPLETED_ONBOARDING] ?: false }

    suspend fun setLoggedIn(isLoggedIn: Boolean, userId: String? = null) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
            if (userId != null) {
                preferences[USER_ID] = userId
            }
        }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ONBOARDING] = completed
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
```

---

### 2.3: Splash ViewModel
**Время**: 30 мин

#### `presentation/splash/SplashViewModel.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<SplashNavigationEvent?>(null)
    val navigationEvent: StateFlow<SplashNavigationEvent?> = _navigationEvent

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            // Показываем splash минимум 2 секунды
            delay(2000)

            val isLoggedIn = userPreferences.isLoggedIn.first()
            val hasCompletedOnboarding = userPreferences.hasCompletedOnboarding.first()

            _navigationEvent.value = when {
                !isLoggedIn -> SplashNavigationEvent.NavigateToWelcome
                !hasCompletedOnboarding -> SplashNavigationEvent.NavigateToOnboarding
                else -> SplashNavigationEvent.NavigateToHome
            }
        }
    }
}

sealed class SplashNavigationEvent {
    object NavigateToWelcome : SplashNavigationEvent()
    object NavigateToOnboarding : SplashNavigationEvent()
    object NavigateToHome : SplashNavigationEvent()
}
```

---

### 2.4: Splash Screen UI
**Время**: 30 мин

#### `presentation/splash/SplashScreen.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.BgPrimary

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val navigationEvent = viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent.value) {
        when (navigationEvent.value) {
            is SplashNavigationEvent.NavigateToWelcome -> {
                navController.navigate(Routes.WELCOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            is SplashNavigationEvent.NavigateToOnboarding -> {
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            is SplashNavigationEvent.NavigateToHome -> {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "GymMind",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "AI-Powered Fitness Coach",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
```

---

### 2.5: MainActivity и NavGraph
**Время**: 30 мин

#### `MainActivity.kt`

```kotlin
package dev.surovtsev.gymmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.surovtsev.gymmind.core.navigation.GymMindNavHost
import dev.surovtsev.gymmind.core.theme.GymMindTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymMindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GymMindNavHost()
                }
            }
        }
    }
}
```

#### `core/navigation/NavGraph.kt`

```kotlin
package dev.surovtsev.gymmind.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.surovtsev.gymmind.presentation.splash.SplashScreen

@Composable
fun GymMindNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        // Остальные экраны будут добавлены позже
    }
}
```

**Результат**: Splash Screen готов, определяет куда направить пользователя

---

## 👋 ШАГ 3: Welcome Screen (1-2 часа)

### 3.1: Welcome Screen UI
**Время**: 1-2 часа

#### `presentation/welcome/WelcomeScreen.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.BgPrimary
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.components.NeonButton

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Hero Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Logo/Image (опционально)
                // Image(...)

                Text(
                    text = "Welcome to\nGymMind AI",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Your AI-powered fitness coach\nfor personalized workouts and\nreal-time form analysis",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            // Features (опционально)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FeatureItem(
                    icon = "🎯",
                    title = "Personalized Programs",
                    description = "Custom workout plans tailored to your goals"
                )
                FeatureItem(
                    icon = "🤖",
                    title = "AI Coach",
                    description = "24/7 fitness guidance and motivation"
                )
                FeatureItem(
                    icon = "📹",
                    title = "Form Analysis",
                    description = "Real-time video feedback on technique"
                )
            }

            // CTA Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NeonButton(
                    text = "Get Started",
                    onClick = { navController.navigate(Routes.LOGIN) }
                )

                Text(
                    text = "By continuing, you agree to our Terms & Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FeatureItem(
    icon: String,
    title: String,
    description: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.displaySmall
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

### 3.2: Добавить в NavGraph
**Время**: 5 мин

В `NavGraph.kt` добавить:

```kotlin
composable(Routes.WELCOME) {
    WelcomeScreen(navController = navController)
}
```

**Результат**: Welcome Screen с красивым приветствием и CTA

---

## 🔐 ШАГ 4: Login Screen (Google Sign-In) (4-6 часов)

### 4.1: Firebase Setup
**Время**: 1 час

1. **Создать Firebase проект** в консоли
2. **Добавить Android app** с пакетом `dev.surovtsev.gymmind`
3. **Скачать `google-services.json`** → поместить в `app/`
4. **Включить Google Sign-In** в Authentication
5. **Настроить SHA-1**: `./gradlew signingReport`

---

### 4.2: Firebase и Credential Manager Modules
**Время**: 1 час

#### `core/di/FirebaseModule.kt`

```kotlin
package dev.surovtsev.gymmind.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}
```

---

### 4.3: Domain Models
**Время**: 30 мин

#### `domain/model/UserProfile.kt`

```kotlin
package dev.surovtsev.gymmind.domain.model

data class UserProfile(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String?,
    val hasCompletedOnboarding: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
```

---

### 4.4: Auth Repository
**Время**: 1 час

#### `domain/repository/AuthRepository.kt`

```kotlin
package dev.surovtsev.gymmind.domain.repository

import dev.surovtsev.gymmind.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<UserProfile>
    suspend fun signOut()
    fun getCurrentUser(): Flow<UserProfile?>
}
```

#### `data/repository/AuthRepositoryImpl.kt`

```kotlin
package dev.surovtsev.gymmind.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.UserProfile
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun signInWithGoogle(idToken: String): Result<UserProfile> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: return Result.failure(Exception("User is null"))

            val profile = UserProfile(
                id = user.uid,
                email = user.email ?: "",
                displayName = user.displayName ?: "",
                photoUrl = user.photoUrl?.toString(),
                hasCompletedOnboarding = false
            )

            // Сохранить в Firestore
            firestore.collection("users")
                .document(user.uid)
                .set(profile)
                .await()

            // Сохранить в DataStore
            userPreferences.setLoggedIn(true, user.uid)

            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        userPreferences.clear()
    }

    override fun getCurrentUser(): Flow<UserProfile?> = flow {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val doc = firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .await()

            val profile = doc.toObject(UserProfile::class.java)
            emit(profile)
        } else {
            emit(null)
        }
    }
}
```

#### `core/di/RepositoryModule.kt`

```kotlin
package dev.surovtsev.gymmind.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.gymmind.data.repository.AuthRepositoryImpl
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
```

---

### 4.5: Login ViewModel
**Время**: 1 час

#### `presentation/login/LoginViewModel.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val credentialManager = CredentialManager.create(context)

                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("YOUR_WEB_CLIENT_ID") // TODO: Добавить из Firebase Console
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential
                if (credential is GoogleIdTokenCredential) {
                    val idToken = credential.idToken

                    authRepository.signInWithGoogle(idToken)
                        .onSuccess {
                            _uiState.value = LoginUiState.Success
                        }
                        .onFailure { error ->
                            _uiState.value = LoginUiState.Error(error.message ?: "Unknown error")
                        }
                } else {
                    _uiState.value = LoginUiState.Error("Invalid credential type")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
```

---

### 4.6: Login Screen UI
**Время**: 1 час

#### `presentation/login/LoginScreen.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.R
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.BgPrimary
import dev.surovtsev.gymmind.presentation.components.GymMindButton

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value) {
        if (uiState.value is LoginUiState.Success) {
            navController.navigate(Routes.ONBOARDING) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Logo
            Text(
                text = "GymMind",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Sign in to get started",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Google Sign-In Button
            GymMindButton(
                text = "Continue with Google",
                onClick = { viewModel.signInWithGoogle(context) },
                isLoading = uiState.value is LoginUiState.Loading
            )

            // Error Message
            if (uiState.value is LoginUiState.Error) {
                Text(
                    text = (uiState.value as LoginUiState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
```

### 4.7: Добавить в NavGraph

```kotlin
composable(Routes.LOGIN) {
    LoginScreen(navController = navController)
}
```

**Результат**: Google Sign-In полностью работает

---

## 📋 ШАГ 5: Onboarding Screen (7 шагов опроса) (6-8 часов)

### 5.1: Domain Models для Onboarding
**Время**: 30 мин

#### `domain/model/OnboardingData.kt`

```kotlin
package dev.surovtsev.gymmind.domain.model

data class OnboardingData(
    val goal: String = "",                    // Step 1: Build Muscle, Lose Weight, etc.
    val experience: String = "",              // Step 2: Beginner, Intermediate, Advanced
    val frequency: Int = 3,                   // Step 3: Workouts per week (1-7)
    val duration: Int = 60,                   // Step 4: Minutes per workout (30-120)
    val equipment: List<String> = emptyList(), // Step 5: Gym, Home, Minimal
    val preferences: List<String> = emptyList(), // Step 6: Focus areas
    val fitnessLevel: String = ""             // Step 7: Current fitness assessment
)
```

---

### 5.2: Repository для сохранения Onboarding
**Время**: 30 мин

Обновить `AuthRepository` и `AuthRepositoryImpl`:

```kotlin
// В AuthRepository.kt
suspend fun saveOnboardingData(userId: String, data: OnboardingData)

// В AuthRepositoryImpl.kt
override suspend fun saveOnboardingData(userId: String, data: OnboardingData) {
    firestore.collection("users")
        .document(userId)
        .update(
            mapOf(
                "onboardingData" to data,
                "hasCompletedOnboarding" to true
            )
        )
        .await()

    userPreferences.setOnboardingCompleted(true)
}
```

---

### 5.3: Onboarding ViewModel
**Время**: 2 часа

#### `presentation/onboarding/OnboardingViewModel.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.gymmind.data.local.UserPreferences
import dev.surovtsev.gymmind.domain.model.OnboardingData
import dev.surovtsev.gymmind.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun setGoal(goal: String) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(goal = goal)
        )
    }

    fun setExperience(experience: String) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(experience = experience)
        )
    }

    fun setFrequency(frequency: Int) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(frequency = frequency)
        )
    }

    fun setDuration(duration: Int) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(duration = duration)
        )
    }

    fun setEquipment(equipment: List<String>) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(equipment = equipment)
        )
    }

    fun setPreferences(preferences: List<String>) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(preferences = preferences)
        )
    }

    fun setFitnessLevel(level: String) {
        _uiState.value = _uiState.value.copy(
            onboardingData = _uiState.value.onboardingData.copy(fitnessLevel = level)
        )
    }

    fun nextStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep < 7) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep + 1)
        }
    }

    fun previousStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep > 1) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep - 1)
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val userId = userPreferences.userId.first() ?: return@launch

            authRepository.saveOnboardingData(userId, _uiState.value.onboardingData)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isCompleted = true
            )
        }
    }
}

data class OnboardingUiState(
    val currentStep: Int = 1,
    val onboardingData: OnboardingData = OnboardingData(),
    val isLoading: Boolean = false,
    val isCompleted: Boolean = false
)
```

---

### 5.4: Onboarding Screen UI (7 шагов)
**Время**: 3-4 часа

#### `presentation/onboarding/OnboardingScreen.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.surovtsev.gymmind.core.navigation.Routes
import dev.surovtsev.gymmind.core.theme.BgPrimary
import dev.surovtsev.gymmind.presentation.components.GymMindButton
import dev.surovtsev.gymmind.presentation.onboarding.steps.*

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.value.isCompleted) {
        if (uiState.value.isCompleted) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.ONBOARDING) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(24.dp)
    ) {
        // Progress Indicator
        OnboardingProgress(
            currentStep = uiState.value.currentStep,
            totalSteps = 7
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Step Content
        Box(modifier = Modifier.weight(1f)) {
            when (uiState.value.currentStep) {
                1 -> Step1Goal(
                    selected = uiState.value.onboardingData.goal,
                    onSelect = { viewModel.setGoal(it) }
                )
                2 -> Step2Experience(
                    selected = uiState.value.onboardingData.experience,
                    onSelect = { viewModel.setExperience(it) }
                )
                3 -> Step3Frequency(
                    frequency = uiState.value.onboardingData.frequency,
                    onFrequencyChange = { viewModel.setFrequency(it) }
                )
                4 -> Step4Duration(
                    duration = uiState.value.onboardingData.duration,
                    onDurationChange = { viewModel.setDuration(it) }
                )
                5 -> Step5Equipment(
                    selected = uiState.value.onboardingData.equipment,
                    onSelect = { viewModel.setEquipment(it) }
                )
                6 -> Step6Preferences(
                    selected = uiState.value.onboardingData.preferences,
                    onSelect = { viewModel.setPreferences(it) }
                )
                7 -> Step7FitnessLevel(
                    selected = uiState.value.onboardingData.fitnessLevel,
                    onSelect = { viewModel.setFitnessLevel(it) }
                )
            }
        }

        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (uiState.value.currentStep > 1) {
                GymMindButton(
                    text = "Back",
                    onClick = { viewModel.previousStep() },
                    modifier = Modifier.weight(1f)
                )
            }

            GymMindButton(
                text = if (uiState.value.currentStep == 7) "Complete" else "Continue",
                onClick = {
                    if (uiState.value.currentStep == 7) {
                        viewModel.completeOnboarding()
                    } else {
                        viewModel.nextStep()
                    }
                },
                modifier = Modifier.weight(1f),
                isLoading = uiState.value.isLoading
            )
        }
    }
}

@Composable
fun OnboardingProgress(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        color = if (index < currentStep) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = MaterialTheme.shapes.small
                    )
            )
        }
    }
}
```

#### Создать компоненты для каждого шага в `presentation/onboarding/steps/`:

**Step1Goal.kt**, **Step2Experience.kt**, **Step3Frequency.kt**, и т.д.

Пример **Step1Goal.kt**:

```kotlin
package dev.surovtsev.gymmind.presentation.onboarding.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.presentation.onboarding.components.SelectionCard

@Composable
fun Step1Goal(
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "What's your primary goal?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        SelectionCard(
            title = "Build Muscle",
            description = "Gain strength and muscle mass",
            isSelected = selected == "Build Muscle",
            onClick = { onSelect("Build Muscle") }
        )

        SelectionCard(
            title = "Lose Weight",
            description = "Burn fat and get lean",
            isSelected = selected == "Lose Weight",
            onClick = { onSelect("Lose Weight") }
        )

        SelectionCard(
            title = "Stay Fit",
            description = "Maintain health and fitness",
            isSelected = selected == "Stay Fit",
            onClick = { onSelect("Stay Fit") }
        )

        SelectionCard(
            title = "Improve Performance",
            description = "Enhance athletic ability",
            isSelected = selected == "Improve Performance",
            onClick = { onSelect("Improve Performance") }
        )
    }
}
```

#### `presentation/onboarding/components/SelectionCard.kt`

```kotlin
package dev.surovtsev.gymmind.presentation.onboarding.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.surovtsev.gymmind.core.theme.BgSurface
import dev.surovtsev.gymmind.core.theme.Divider
import dev.surovtsev.gymmind.core.theme.Primary

@Composable
fun SelectionCard(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = BgSurface),
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) Primary else Divider
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

*Создать аналогичные файлы для Step2-Step7*

### 5.5: Добавить в NavGraph

```kotlin
composable(Routes.ONBOARDING) {
    OnboardingScreen(navController = navController)
}
```

**Результат**: 7-шаговый onboarding flow полностью работает

---

## 🏠 ШАГ 6: Home Screen (Главный экран Dashboard) (8-12 часов)

*Эта секция будет включать полную реализацию Home screen с:*
- Приветствием пользователя
- Активной программой
- Сегодняшней тренировкой
- Статистикой
- Быстрыми действиями

### 6.1: Database для Workout Programs

*Создать Entity, DAO, Repository для программ тренировок...*

### 6.2: Home ViewModel

*ViewModel для загрузки данных dashboard...*

### 6.3: Home Screen UI

*Composable с виджетами...*

---

## 💪 ШАГ 7: Workout Execution Screen

*Полная реализация экрана выполнения тренировки...*

---

## 📚 ШАГ 8: Programs Screen

*Библиотека программ тренировок...*

---

## 🤖 ШАГ 9: AI Coach Screen

*Чат с AI тренером...*

---

## 👤 ШАГ 10: Profile Screen

*Профиль пользователя...*

---

## 📹 ШАГ 11: Video Analysis

*CameraX + MediaPipe для анализа формы...*

---

## 🎯 ИТОГО

**Всего шагов**: ~15-20 экранов/фич
**Общее время**: 80-120 часов
**Подход**: Каждый экран реализуется полностью от начала до конца

Каждый шаг включает:
1. Domain модели (если нужны)
2. Entity + DAO (если нужна БД)
3. Repository интерфейс + реализация
4. ViewModel с state management
5. UI Screen (Compose)
6. Навигация

Такой подход позволяет тестировать каждый экран сразу после реализации и видеть прогресс.
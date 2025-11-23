# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

### Build
- **Clean build**: `./gradlew clean`
- **Build debug**: `./gradlew assembleDebug`
- **Build release**: `./gradlew assembleRelease`
- **Install on device**: `./gradlew installDebug`

### Code Quality
- **Lint**: `./gradlew lint`
- **Lint report location**: `app/build/reports/lint-results.html`

### Testing
- **Run unit tests**: `./gradlew test`
- **Run instrumented tests**: `./gradlew connectedAndroidTest`
- **Run specific test**: `./gradlew test --tests "TestClassName"`

### Dependencies
- **Refresh dependencies**: `./gradlew --refresh-dependencies`
- **View dependency tree**: `./gradlew app:dependencies`

## Project Architecture

### Clean Architecture Structure
This project follows Clean Architecture principles with three layers:

1. **Domain Layer** (`domain/`)
   - Contains business logic, models, and repository interfaces
   - No Android dependencies
   - Core models: `UserProfile`, `WorkoutPlan`, `Exercise`, fitness enums

2. **Data Layer** (`data/`)
   - Implements repositories defined in domain
   - `local/`: DataStore (UserPreferences) for simple key-value storage
   - `repository/`: Repository implementations (AuthRepository, OnboardingRepository, WorkoutRepository)

3. **Presentation Layer** (`presentation/`)
   - Jetpack Compose UI with MVVM pattern
   - Each screen has its own ViewModel following naming: `ScreenNameViewModel`
   - Screens: Splash → Onboarding → Home → Profile

### Dependency Injection (Hilt)
- All modules in `core/di/`
- Key modules:
  - `AppModule`: Application-level dependencies
  - `DataStoreModule`: DataStore configuration
  - `FirebaseModule`: Firebase Auth, Firestore, Storage
  - `RepositoryModule`: Repository bindings

### Navigation
- Centralized in `core/navigation/NavGraph.kt`
- Routes defined in `core/navigation/Routes.kt`
- Uses Jetpack Compose Navigation

### State Management
- **UserPreferences (DataStore)**: Stores login state, onboarding completion, guest mode
- **Firebase Firestore**: User profiles and workout data
- **ViewModel StateFlow/Flow**: UI state management

## Key Features & Implementation Notes

### Onboarding Flow
- Dynamic, branching questionnaire system (7-10 cards depending on answers)
- Logic handled by use cases: `GetNextCardUseCase`, `GetPreviousCardUseCase`, `CalculateProgressUseCase`
- State saved to UserProfile in Firestore on completion
- Conditional cards based on:
  - Goal (lose/gain weight → show target weight card)
  - Experience level (beginner → show basic skills assessment)
  - Location (home with equipment/gym → show equipment selection)

### Authentication
- Firebase Authentication with Google Sign-In
- Credential Manager API for modern authentication flow
- Guest mode supported (stored in UserPreferences)
- User profiles stored in Firestore `users` collection

### Design System
- Custom Material3 theme in `core/theme/`
- "Neon Focus" dark theme with blue/cyan accents
- Inter font family (regular, medium, semibold, bold)
- Custom components in `presentation/components/`: Buttons, AnimatedNeonBackground

### Backend Configuration
- Debug builds point to local backend: `http://10.0.2.2:3000/`
- Release builds point to production: `https://api.gymmind.ai/`
- Configured in `app/build.gradle.kts` buildTypes

## Important Development Patterns

### Adding New Screens
1. Create model in `domain/model/` if needed
2. Create repository interface in `domain/repository/`
3. Implement repository in `data/repository/`
4. Add binding in `RepositoryModule.kt`
5. Create ViewModel in `presentation/screenname/`
6. Create Composable screen in same package
7. Add route to `Routes.kt`
8. Add composable to `NavGraph.kt`

### Working with Firebase
- All Firebase instances injected via Hilt (FirebaseAuth, Firestore, Storage)
- User data stored under `users/{userId}` document
- UserProfile contains all onboarding data

### MultiDex
- Enabled due to large dependency count (Firebase, MediaPipe, CameraX, etc.)
- Configured in `GymMindApplication.kt`

## Common Tasks

### Update User Profile After Onboarding
User profile fields are populated during onboarding and saved to Firestore. All onboarding-related fields are part of the `UserProfile` data class.

### Add New Fitness Enum
Enums are defined in `domain/model/FitnessEnums.kt`: Gender, FitnessGoal, ExperienceLevel, WorkoutLocation, Equipment, DaysPerWeek, MinutesPerWorkout, HealthLimitation, NotificationTime.

### Test Guest Mode vs Authenticated Mode
Check `UserPreferences.isGuestMode` and `UserPreferences.isLoggedIn` flows. Guest mode skips authentication but still allows onboarding.

## File Locations
- Gradle version catalog: `gradle/libs.versions.toml`
- Firebase config: `app/google-services.json`
- Application class: `app/src/main/java/dev/surovtsev/gymmind/GymMindApplication.kt`
- String resources: `app/src/main/res/values/strings.xml`
- Font files: `app/src/main/res/font/`

## Notes
- Minimum SDK: API 26 (Android 8.0)
- Target SDK: API 36
- Kotlin version: 2.0.21
- Compose BOM: 2025.11.00
- Russian comments in code are acceptable (developer preference)
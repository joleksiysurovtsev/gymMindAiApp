# Инструкция для AI агента: Реализация Onboarding для Android фитнес-приложения

## Контекст задачи
Создать onboarding flow для Android фитнес-приложения с AI тренером. Система должна собирать данные пользователя через серию карточек с умной логикой ветвления (7-10 карточек в зависимости от ответов).


## Структура проекта

```
app/
├── data/
│   ├── model/
│   │   └── OnboardingData.kt          # Data класс для всех данных
│   └── repository/
│       └── OnboardingRepository.kt     # Сохранение/загрузка данных
├── domain/
│   ├── model/
│   │   └── OnboardingState.kt         # Domain модель состояния
│   └── usecase/
│       └── GetNextCardUseCase.kt      # Логика ветвления
├── presentation/
│   ├── onboarding/
│   │   ├── OnboardingViewModel.kt     # ViewModel
│   │   ├── OnboardingScreen.kt        # Главный экран
│   │   └── cards/                     # Компоненты карточек
│   │       ├── BaseCard.kt
│   │       ├── BasicInfoCard.kt
│   │       ├── GoalCard.kt
│   │       ├── TargetWeightCard.kt
│   │       ├── ExperienceCard.kt
│   │       ├── LocationCard.kt
│   │       ├── EquipmentCard.kt
│   │       ├── ScheduleCard.kt
│   │       ├── LimitationsCard.kt
│   │       ├── BasicSkillsCard.kt
│   │       └── NotificationsCard.kt
│   └── navigation/
│       └── OnboardingNavigation.kt
```

## 1. Data Model (OnboardingData.kt)

```kotlin
data class OnboardingData(
    // Карточка 1: Базовая информация
    val gender: Gender? = null,
    val age: Int? = null,
    val height: Int? = null,  // см
    val currentWeight: Float? = null,  // кг
    
    // Карточка 2: Главная цель
    val goal: FitnessGoal? = null,
    
    // Карточка 3А: Целевой вес (условно)
    val targetWeight: Float? = null,
    
    // Карточка 4: Уровень подготовки
    val experienceLevel: ExperienceLevel? = null,
    
    // Карточка 5: Место тренировок
    val location: WorkoutLocation? = null,
    
    // Карточка 5А: Оборудование (условно)
    val equipment: Set<Equipment> = emptySet(),
    
    // Карточка 6: Частота и время
    val daysPerWeek: DaysPerWeek? = null,
    val minutesPerWorkout: MinutesPerWorkout? = null,
    
    // Карточка 7: Ограничения
    val limitations: Set<HealthLimitation> = emptySet(),
    val customLimitation: String? = null,
    
    // Карточка 8А: Базовые навыки (условно)
    val canDoSquats: Boolean? = null,
    val canDoPushUps: Boolean? = null,
    val canDoPlank: Boolean? = null,
    
    // Карточка 9: Уведомления
    val notificationTime: NotificationTime? = null,
    
    // Мета-данные
    val isCompleted: Boolean = false,
    val currentStep: Int = 0
)

enum class Gender { MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY }

enum class FitnessGoal {
    LOSE_WEIGHT,        // Похудеть → показать целевой вес
    GAIN_MUSCLE,        // Набрать массу → показать целевой вес
    IMPROVE_FLEXIBILITY, // Гибкость → пропустить оборудование
    IMPROVE_ENDURANCE,
    MAINTAIN_FITNESS
}

enum class ExperienceLevel {
    BEGINNER,          // → показать проверку базовых навыков
    INTERMEDIATE,      // → пропустить проверку
    ADVANCED           // → пропустить проверку
}

enum class WorkoutLocation {
    HOME_NO_EQUIPMENT,    // → пропустить выбор оборудования
    HOME_WITH_EQUIPMENT,  // → показать выбор оборудования
    GYM,                  // → показать выбор оборудования
    OUTDOOR               // → пропустить выбор оборудования
}

enum class Equipment {
    DUMBBELLS, BARBELL, PULL_UP_BAR, RESISTANCE_BANDS, 
    BENCH, MACHINES, CARDIO_MACHINES
}

enum class DaysPerWeek { TWO_THREE, FOUR_FIVE, SIX_SEVEN }

enum class MinutesPerWorkout { SHORT, MEDIUM, LONG }

enum class HealthLimitation {
    NONE, BACK_ISSUES, KNEE_ISSUES, SHOULDER_ISSUES, 
    CARDIOVASCULAR, PREGNANCY, OTHER
}

enum class NotificationTime {
    MORNING, AFTERNOON, EVENING, ANYTIME, NONE
}
```

## 2. Логика ветвления (GetNextCardUseCase.kt)

```kotlin
class GetNextCardUseCase {
    
    fun getNextCard(currentCard: CardType, data: OnboardingData): CardType? {
        return when (currentCard) {
            CardType.BASIC_INFO -> CardType.GOAL
            
            CardType.GOAL -> {
                when (data.goal) {
                    FitnessGoal.LOSE_WEIGHT, FitnessGoal.GAIN_MUSCLE -> 
                        CardType.TARGET_WEIGHT
                    FitnessGoal.IMPROVE_FLEXIBILITY -> 
                        CardType.SCHEDULE // пропускаем опыт и оборудование
                    else -> CardType.EXPERIENCE
                }
            }
            
            CardType.TARGET_WEIGHT -> CardType.EXPERIENCE
            
            CardType.EXPERIENCE -> {
                if (data.goal == FitnessGoal.IMPROVE_FLEXIBILITY) {
                    CardType.SCHEDULE
                } else {
                    CardType.LOCATION
                }
            }
            
            CardType.LOCATION -> {
                when (data.location) {
                    WorkoutLocation.HOME_WITH_EQUIPMENT, 
                    WorkoutLocation.GYM -> CardType.EQUIPMENT
                    else -> CardType.SCHEDULE
                }
            }
            
            CardType.EQUIPMENT -> CardType.SCHEDULE
            
            CardType.SCHEDULE -> CardType.LIMITATIONS
            
            CardType.LIMITATIONS -> {
                when (data.experienceLevel) {
                    ExperienceLevel.BEGINNER -> CardType.BASIC_SKILLS
                    else -> CardType.NOTIFICATIONS
                }
            }
            
            CardType.BASIC_SKILLS -> CardType.NOTIFICATIONS
            
            CardType.NOTIFICATIONS -> null // Конец
        }
    }
    
    fun getPreviousCard(currentCard: CardType, data: OnboardingData): CardType? {
        // Обратная логика для кнопки "Назад"
        return when (currentCard) {
            CardType.BASIC_INFO -> null
            CardType.GOAL -> CardType.BASIC_INFO
            CardType.TARGET_WEIGHT -> CardType.GOAL
            CardType.EXPERIENCE -> {
                if (data.goal in listOf(FitnessGoal.LOSE_WEIGHT, FitnessGoal.GAIN_MUSCLE)) {
                    CardType.TARGET_WEIGHT
                } else {
                    CardType.GOAL
                }
            }
            // ... остальная логика
            else -> null
        }
    }
    
    fun getTotalSteps(data: OnboardingData): Int {
        var steps = 4 // Базовые: Info, Goal, Experience, Schedule, Limitations, Notifications
        
        if (data.goal in listOf(FitnessGoal.LOSE_WEIGHT, FitnessGoal.GAIN_MUSCLE)) {
            steps++ // Target Weight
        }
        
        if (data.goal != FitnessGoal.IMPROVE_FLEXIBILITY) {
            steps++ // Location
            if (data.location in listOf(WorkoutLocation.HOME_WITH_EQUIPMENT, WorkoutLocation.GYM)) {
                steps++ // Equipment
            }
        }
        
        if (data.experienceLevel == ExperienceLevel.BEGINNER) {
            steps++ // Basic Skills
        }
        
        return steps
    }
}

enum class CardType {
    BASIC_INFO, GOAL, TARGET_WEIGHT, EXPERIENCE, LOCATION, 
    EQUIPMENT, SCHEDULE, LIMITATIONS, BASIC_SKILLS, NOTIFICATIONS
}
```

## 3. ViewModel (OnboardingViewModel.kt)

```kotlin
class OnboardingViewModel(
    private val getNextCardUseCase: GetNextCardUseCase,
    private val repository: OnboardingRepository
) : ViewModel() {
    
    private val _onboardingData = MutableStateFlow(OnboardingData())
    val onboardingData: StateFlow<OnboardingData> = _onboardingData.asStateFlow()
    
    private val _currentCard = MutableStateFlow(CardType.BASIC_INFO)
    val currentCard: StateFlow<CardType> = _currentCard.asStateFlow()
    
    private val _canGoBack = MutableStateFlow(false)
    val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()
    
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()
    
    fun updateBasicInfo(gender: Gender, age: Int, height: Int, weight: Float) {
        _onboardingData.value = _onboardingData.value.copy(
            gender = gender,
            age = age,
            height = height,
            currentWeight = weight
        )
    }
    
    fun updateGoal(goal: FitnessGoal) {
        _onboardingData.value = _onboardingData.value.copy(goal = goal)
    }
    
    fun updateTargetWeight(weight: Float) {
        _onboardingData.value = _onboardingData.value.copy(targetWeight = weight)
    }
    
    fun updateExperienceLevel(level: ExperienceLevel) {
        _onboardingData.value = _onboardingData.value.copy(experienceLevel = level)
    }
    
    fun updateLocation(location: WorkoutLocation) {
        _onboardingData.value = _onboardingData.value.copy(location = location)
    }
    
    fun updateEquipment(equipment: Set<Equipment>) {
        _onboardingData.value = _onboardingData.value.copy(equipment = equipment)
    }
    
    fun updateSchedule(days: DaysPerWeek, minutes: MinutesPerWorkout) {
        _onboardingData.value = _onboardingData.value.copy(
            daysPerWeek = days,
            minutesPerWorkout = minutes
        )
    }
    
    fun updateLimitations(limitations: Set<HealthLimitation>, custom: String? = null) {
        _onboardingData.value = _onboardingData.value.copy(
            limitations = limitations,
            customLimitation = custom
        )
    }
    
    fun updateBasicSkills(squats: Boolean, pushUps: Boolean, plank: Boolean) {
        _onboardingData.value = _onboardingData.value.copy(
            canDoSquats = squats,
            canDoPushUps = pushUps,
            canDoPlank = plank
        )
    }
    
    fun updateNotificationTime(time: NotificationTime) {
        _onboardingData.value = _onboardingData.value.copy(
            notificationTime = time
        )
    }
    
    fun goToNextCard() {
        val nextCard = getNextCardUseCase.getNextCard(
            _currentCard.value, 
            _onboardingData.value
        )
        
        if (nextCard != null) {
            _currentCard.value = nextCard
            _canGoBack.value = true
            updateProgress()
        } else {
            // Onboarding завершен
            completeOnboarding()
        }
    }
    
    fun goToPreviousCard() {
        val prevCard = getNextCardUseCase.getPreviousCard(
            _currentCard.value,
            _onboardingData.value
        )
        
        if (prevCard != null) {
            _currentCard.value = prevCard
            _canGoBack.value = prevCard != CardType.BASIC_INFO
            updateProgress()
        }
    }
    
    private fun updateProgress() {
        val currentStep = getCurrentStep()
        val totalSteps = getNextCardUseCase.getTotalSteps(_onboardingData.value)
        _progress.value = currentStep.toFloat() / totalSteps.toFloat()
    }
    
    private fun getCurrentStep(): Int {
        // Подсчет текущего шага на основе пройденных карточек
        var step = 0
        // ... логика подсчета
        return step
    }
    
    private fun completeOnboarding() {
        viewModelScope.launch {
            _onboardingData.value = _onboardingData.value.copy(isCompleted = true)
            repository.saveOnboardingData(_onboardingData.value)
            // Навигация на главный экран
        }
    }
}
```

## 4. UI Screen (OnboardingScreen.kt)

```kotlin
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onComplete: () -> Unit
) {
    val currentCard by viewModel.currentCard.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    val progress by viewModel.progress.collectAsState()
    
    Scaffold(
        topBar = {
            OnboardingTopBar(
                progress = progress,
                canGoBack = canGoBack,
                onBackClick = { viewModel.goToPreviousCard() }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedContent(
                targetState = currentCard,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() with
                    slideOutHorizontally { -it } + fadeOut()
                }
            ) { card ->
                when (card) {
                    CardType.BASIC_INFO -> BasicInfoCard(
                        onNext = { gender, age, height, weight ->
                            viewModel.updateBasicInfo(gender, age, height, weight)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.GOAL -> GoalCard(
                        onSelect = { goal ->
                            viewModel.updateGoal(goal)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.TARGET_WEIGHT -> TargetWeightCard(
                        currentWeight = viewModel.onboardingData.value.currentWeight ?: 0f,
                        onNext = { weight ->
                            viewModel.updateTargetWeight(weight)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.EXPERIENCE -> ExperienceCard(
                        onSelect = { level ->
                            viewModel.updateExperienceLevel(level)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.LOCATION -> LocationCard(
                        onSelect = { location ->
                            viewModel.updateLocation(location)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.EQUIPMENT -> EquipmentCard(
                        onNext = { equipment ->
                            viewModel.updateEquipment(equipment)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.SCHEDULE -> ScheduleCard(
                        onNext = { days, minutes ->
                            viewModel.updateSchedule(days, minutes)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.LIMITATIONS -> LimitationsCard(
                        onNext = { limitations, custom ->
                            viewModel.updateLimitations(limitations, custom)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.BASIC_SKILLS -> BasicSkillsCard(
                        onNext = { squats, pushUps, plank ->
                            viewModel.updateBasicSkills(squats, pushUps, plank)
                            viewModel.goToNextCard()
                        }
                    )
                    
                    CardType.NOTIFICATIONS -> NotificationsCard(
                        onComplete = { time ->
                            viewModel.updateNotificationTime(time)
                            viewModel.goToNextCard()
                            onComplete()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingTopBar(
    progress: Float,
    canGoBack: Boolean,
    onBackClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                enabled = canGoBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = if (canGoBack) MaterialTheme.colorScheme.primary 
                           else Color.Transparent
                )
            }
            
            Text(
                text = "Настройка профиля",
                style = MaterialTheme.typography.titleMedium
            )
            
            // Placeholder для симметрии
            Spacer(modifier = Modifier.size(48.dp))
        }
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

## 5. Пример карточки (GoalCard.kt)

```kotlin
@Composable
fun GoalCard(onSelect: (FitnessGoal) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Какая ваша главная цель?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        GoalOption(
            icon = "🔥",
            title = "Похудеть",
            description = "Снизить процент жира",
            onClick = { onSelect(FitnessGoal.LOSE_WEIGHT) }
        )
        
        GoalOption(
            icon = "💪",
            title = "Набрать мышечную массу",
            description = "Увеличить силу и объем мышц",
            onClick = { onSelect(FitnessGoal.GAIN_MUSCLE) }
        )
        
        GoalOption(
            icon = "🧘",
            title = "Улучшить гибкость",
            description = "Растяжка и мобильность",
            onClick = { onSelect(FitnessGoal.IMPROVE_FLEXIBILITY) }
        )
        
        GoalOption(
            icon = "⚡",
            title = "Повысить выносливость",
            description = "Улучшить кардио",
            onClick = { onSelect(FitnessGoal.IMPROVE_ENDURANCE) }
        )
        
        GoalOption(
            icon = "🎯",
            title = "Поддерживать форму",
            description = "Оставаться в хорошей форме",
            onClick = { onSelect(FitnessGoal.MAINTAIN_FITNESS) }
        )
    }
}

@Composable
fun GoalOption(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
```

## 6. Repository (OnboardingRepository.kt)

```kotlin
class OnboardingRepository(private val dataStore: DataStore<Preferences>) {
    
    private val ONBOARDING_KEY = stringPreferencesKey("onboarding_data")
    
    suspend fun saveOnboardingData(data: OnboardingData) {
        val json = Json.encodeToString(data)
        dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = json
        }
    }
    
    fun getOnboardingData(): Flow<OnboardingData?> {
        return dataStore.data.map { preferences ->
            preferences[ONBOARDING_KEY]?.let { json ->
                Json.decodeFromString<OnboardingData>(json)
            }
        }
    }
    
    suspend fun clearOnboardingData() {
        dataStore.edit { it.clear() }
    }
}
```

## Требования к реализации:

### UI/UX:
1. **Анимации:** Плавные переходы между карточками (slide + fade)
2. **Progress bar:** Динамический расчет прогресса с учетом пропущенных карточек
3. **Валидация:** Кнопка "Далее" активна только при заполнении обязательных полей
4. **Адаптивность:** Поддержка разных размеров экранов
5. **Dark mode:** Поддержка темной темы

### Логика:
1. **Сохранение состояния:** При сворачивании приложения данные не должны теряться
2. **Возврат назад:** Корректная работа с учетом пропущенных карточек
3. **Пропуск карточек:** Кнопка "Пропустить" на опциональных экранах (ограничения)

### Тестирование:
1. Unit тесты для `GetNextCardUseCase` - проверка всех веток логики
2. UI тесты для критических путей
3. Screenshot тесты для карточек

## Дополнительные фичи (опционально):
- Возможность вернуться к onboarding из настроек
- A/B тестирование порядка вопросов
- Аналитика: отслеживание на каких карточках пользователи застревают
- Пре-заполнение данных из Google Fit / Health Connect

Начни реализацию с создания data моделей и базовой навигации, затем добавь UI карточки одну за другой.
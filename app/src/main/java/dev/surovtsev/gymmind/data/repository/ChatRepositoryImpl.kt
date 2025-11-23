package dev.surovtsev.gymmind.data.repository

import dev.surovtsev.gymmind.domain.model.ChatMessage
import dev.surovtsev.gymmind.domain.model.MessageSender
import dev.surovtsev.gymmind.domain.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor() : ChatRepository {

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                content = "Привет! Я твой AI-тренер. Я помогу тебе составить программу тренировок, подскажу технику упражнений и отвечу на вопросы о фитнесе. Чем могу помочь?",
                sender = MessageSender.AI_COACH,
                timestamp = Instant.now()
            )
        )
    )

    override suspend fun sendMessage(message: String): Result<ChatMessage> {
        return try {
            // Добавляем сообщение пользователя
            val userMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = message,
                sender = MessageSender.USER,
                timestamp = Instant.now()
            )

            _messages.value = _messages.value + userMessage

            // Имитация задержки для "печатания"
            delay(1500)

            // Генерируем ответ AI
            val aiResponse = generateAIResponse(message)
            val aiMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = aiResponse,
                sender = MessageSender.AI_COACH,
                timestamp = Instant.now()
            )

            _messages.value = _messages.value + aiMessage

            Result.success(aiMessage)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMessages(): Flow<List<ChatMessage>> = _messages

    override suspend fun clearHistory() {
        _messages.value = listOf(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                content = "История чата очищена. Чем могу помочь?",
                sender = MessageSender.AI_COACH,
                timestamp = Instant.now()
            )
        )
    }

    private fun generateAIResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()

        return when {
            // Приветствия
            lowerMessage.contains("привет") || lowerMessage.contains("здравствуй") -> {
                listOf(
                    "Привет! Готов помочь тебе с тренировками!",
                    "Здравствуй! Чем могу быть полезен?",
                    "Привет! Расскажи, какая у тебя цель?"
                ).random()
            }

            // Вопросы о программе
            lowerMessage.contains("программ") && (lowerMessage.contains("выбра") || lowerMessage.contains("подобра")) -> {
                "Для подбора программы мне нужно знать:\n\n" +
                        "1. Твоя цель (набор массы, похудение, поддержание формы)\n" +
                        "2. Опыт тренировок (новичок, средний, продвинутый)\n" +
                        "3. Где тренируешься (дома, в зале)\n" +
                        "4. Сколько раз в неделю можешь заниматься\n\n" +
                        "Расскажи об этом, и я порекомендую подходящую программу!"
            }

            // Похудение
            lowerMessage.contains("похуд") || lowerMessage.contains("вес") && lowerMessage.contains("сброс") -> {
                "Для эффективного похудения рекомендую:\n\n" +
                        "• Дефицит калорий 300-500 ккал\n" +
                        "• Силовые тренировки 3-4 раза в неделю\n" +
                        "• Кардио 2-3 раза по 30-40 минут\n" +
                        "• Достаточный сон (7-8 часов)\n\n" +
                        "Посмотри программу 'Жиросжигание Pro' в разделе Программы!"
            }

            // Набор массы
            lowerMessage.contains("масс") && (lowerMessage.contains("набор") || lowerMessage.contains("набра")) -> {
                "Для набора мышечной массы важно:\n\n" +
                        "• Профицит калорий (+300-500 ккал)\n" +
                        "• Белок 1.6-2г на кг веса\n" +
                        "• Силовые тренировки 4-5 раз в неделю\n" +
                        "• Прогрессия нагрузок\n" +
                        "• Восстановление и сон\n\n" +
                        "Программа 'Масса за 16 недель' отлично подойдет!"
            }

            // Техника упражнений
            lowerMessage.contains("техник") || lowerMessage.contains("как делать") || lowerMessage.contains("как выполн") -> {
                "Правильная техника - основа безопасных и эффективных тренировок!\n\n" +
                        "О какомупражнении хочешь узнать? Например:\n" +
                        "• Приседания\n" +
                        "• Жим лежа\n" +
                        "• Становая тяга\n" +
                        "• Подтягивания\n" +
                        "• Отжимания"
            }

            // Присед
            lowerMessage.contains("присед") || lowerMessage.contains("приседа") -> {
                "Техника приседаний:\n\n" +
                        "1. Ноги на ширине плеч\n" +
                        "2. Носки слегка развернуты\n" +
                        "3. Спина прямая, взгляд вперед\n" +
                        "4. Опускаемся до параллели бедра с полом\n" +
                        "5. Колени не выходят за носки\n" +
                        "6. Вес на пятках\n" +
                        "7. Подъем - выдох, опускание - вдох\n\n" +
                        "Начни с собственного веса, отработай технику!"
            }

            // Боль/травма
            lowerMessage.contains("боль") || lowerMessage.contains("болит") || lowerMessage.contains("травм") -> {
                "⚠️ Если чувствуешь боль или дискомфорт:\n\n" +
                        "1. Прекрати упражнение немедленно\n" +
                        "2. Не тренируй болезненную зону\n" +
                        "3. Обратись к врачу или физиотерапевту\n" +
                        "4. Не занимайся самолечением\n\n" +
                        "Здоровье важнее любых результатов!"
            }

            // Питание
            lowerMessage.contains("питан") || lowerMessage.contains("диет") || lowerMessage.contains("еда") -> {
                "По поводу питания:\n\n" +
                        "• Основа - дефицит/профицит калорий (зависит от цели)\n" +
                        "• Белки: 1.6-2г на кг веса\n" +
                        "• Углеводы: 3-5г на кг (для энергии)\n" +
                        "• Жиры: 0.8-1г на кг (для гормонов)\n" +
                        "• Вода: 30-40мл на кг веса\n\n" +
                        "Для точного плана лучше обратиться к нутрициологу!"
            }

            // Мотивация
            lowerMessage.contains("мотивац") || lowerMessage.contains("лень") || lowerMessage.contains("не хочу") -> {
                listOf(
                    "Помни: каждая тренировка приближает тебя к цели! Даже если не хочется, сделай хотя бы легкую разминку - это лучше, чем ничего.",
                    "Мотивация приходит и уходит, дисциплина остается. Просто начни, и настроение придет в процессе!",
                    "Представь, каким ты будешь через 3 месяца, если не пропустишь ни одной тренировки. Это того стоит!"
                ).random()
            }

            // Новичок
            lowerMessage.contains("новичок") || lowerMessage.contains("начинающ") || lowerMessage.contains("первый раз") -> {
                "Отлично, что решил начать!\n\n" +
                        "Советы для новичков:\n" +
                        "1. Начни с программы 'Старт в зале'\n" +
                        "2. Первый месяц - фокус на технике, не на весах\n" +
                        "3. Не пропускай разминку и заминку\n" +
                        "4. Тренируйся 3 раза в неделю\n" +
                        "5. Высыпайся и правильно питайся\n\n" +
                        "Главное - регулярность и терпение!"
            }

            // Благодарность
            lowerMessage.contains("спасибо") || lowerMessage.contains("благодар") -> {
                listOf(
                    "Всегда пожалуйста! Обращайся, если нужна помощь!",
                    "Рад помочь! Удачных тренировок!",
                    "Не за что! Достигай своих целей!"
                ).random()
            }

            // Вопросы
            lowerMessage.contains("?") -> {
                "Отличный вопрос! Постараюсь помочь, но учти, что я - AI-ассистент с базовыми знаниями. " +
                        "Для сложных вопросов лучше проконсультироваться с тренером или врачом.\n\n" +
                        "Уточни, пожалуйста, что именно тебя интересует?"
            }

            // Все остальное
            else -> {
                listOf(
                    "Понял тебя! Могу подробнее рассказать про тренировки, технику упражнений или питание. Что интересует?",
                    "Хороший вопрос! Расскажи подробнее, и я постараюсь помочь.",
                    "Давай разберемся вместе! Задавай вопросы про тренировки, я здесь, чтобы помогать.",
                    "Интересно! А какая у тебя сейчас цель? Похудение, набор массы или поддержание формы?"
                ).random()
            }
        }
    }
}

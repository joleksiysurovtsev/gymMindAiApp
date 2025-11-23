package dev.surovtsev.gymmind.data.local.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate

/**
 * Type Converters для Room Database
 */
class InstantConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun toInstant(millis: Long?): Instant? =
        millis?.let { Instant.ofEpochMilli(it) }
}

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? =
        dateString?.let { LocalDate.parse(it) }
}

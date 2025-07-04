package com.icdid.dashboard.presentation.util

import com.icdid.core.presentation.utils.UiText
import com.icdid.dashboard.presentation.R
import com.icdid.dashboard.presentation.model.NotesContentType
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

fun String.toInitials(): String {
    // Remove extra whitespace and split by spaces
    val words = this.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }

    return when {
        words.isEmpty() -> ""
        words.size == 1 -> {
            // Single word: take first two characters
            val word = words[0]
            if (word.length >= 2) {
                word.substring(0, 2).uppercase()
            } else {
                word.uppercase()
            }
        }
        words.size == 2 -> {
            // Two words: first character of each word
            "${words[0].first().uppercase()}${words[1].first().uppercase()}"
        }
        else -> {
            // More than two words: first character of first and last word
            "${words.first().first().uppercase()}${words.last().first().uppercase()}"
        }
    }
}

fun String.toDisplayDate(timeZone: ZoneId = ZoneId.systemDefault()): String {
    return try {
        val instant = Instant.parse(this)
        val noteDate = instant.atZone(timeZone).toLocalDate()
        val currentDate = LocalDate.now(timeZone)

        val formatter = if (noteDate.year == currentDate.year) {
            DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())
        } else {
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.getDefault())
        }

        noteDate.format(formatter)

    } catch (e: DateTimeParseException) {
        this
    }
}

fun String.toContentPreview(deviceType: NotesContentType): String {
    val limit = deviceType.characterLimit

    return if (this.length <= limit) {
        this
    } else {
        this.substring(0, limit - 1) + "…"
    }
}

fun String.formatDate(): UiText {
    val instant = Instant.parse(this)
    val now = Instant.now()

    val duration = Duration.between(instant, now)

    return if (duration.toMinutes() < 5) {
        UiText.StringResource(R.string.just_now)
    } else {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        UiText.DynamicString(localDateTime.format(formatter))
    }
}
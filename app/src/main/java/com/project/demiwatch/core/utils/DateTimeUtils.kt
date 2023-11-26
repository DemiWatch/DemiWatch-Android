package com.project.demiwatch.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun formatDateTimeRange(startString: String, endString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val startDate: Date = format.parse(startString) ?: return "Invalid start date"
        val endDate: Date = format.parse(endString) ?: return "Invalid end date"

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val datePart = dateFormat.format(startDate)
        val startTimePart = timeFormat.format(startDate)
        val endTimePart = timeFormat.format(endDate)

        return "$datePart ($startTimePart - $endTimePart)"
    }

    fun formatDuration(duration: String): String {
        val parts = duration.split(", ")
        val hours = parts.getOrNull(0)?.split(" ")?.getOrNull(0) ?: "0"
        val minutes = parts.getOrNull(1)?.split(" ")?.getOrNull(0) ?: "0"

        return "$hours jam $minutes menit"
    }

}
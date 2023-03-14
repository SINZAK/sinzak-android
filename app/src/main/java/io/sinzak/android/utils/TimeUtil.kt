package io.sinzak.android.utils

import java.lang.Exception
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object TimeUtil {

    fun getTimePassed(date : String) : String {
        try {
            val time = DateFormats.MySqlFormat.parse(date.replace('T', ' '))
            val timeGap = (System.currentTimeMillis() - time.time)/ 1000L / 3600



            when{
                timeGap >= 365 * 24 ->
                    return "· ${(timeGap / (365 * 24)).toInt()}년전"
                timeGap >= 24 * 30->
                    return "· ${(timeGap / 24 / 30).toInt()}개월 전"
                timeGap >= 24 ->
                    return "· ${(timeGap / 24).toInt()}일 전"
                timeGap >= 1 ->
                    return "· ${timeGap.toInt()}시간 전"
                else ->
                    return "· 방금 전"
            }

        } catch (e: Exception)
        {

        }
        return "· 방금 전"
    }

    fun getTimePassedExceptDot(date : String) : String {
        try {
            val time = DateFormats.MySqlFormat.parse(date.replace('T', ' '))
            val timeGap = (System.currentTimeMillis() - time.time)/ 1000L / 3600



            when{
                timeGap >= 365 * 24 ->
                    return "${(timeGap / (365 * 24)).toInt()}년전"
                timeGap >= 24 * 30->
                    return "${(timeGap / 24 / 30).toInt()}개월 전"
                timeGap >= 24 ->
                    return "${(timeGap / 24).toInt()}일 전"
                timeGap >= 1 ->
                    return "${timeGap.toInt()}시간 전"
                else ->
                    return "방금 전"
            }

        } catch (e: Exception)
        {

        }
        return "방금 전"
    }

    fun dateTimeToString(dateTimeString: String): String {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("a h:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)
        return dateTime.format(outputFormatter)
    }

    fun getCurrentDateTimeString(): String {
        val currentDateTime = LocalDateTime.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return currentDateTime.format(dateTimeFormatter)
    }

}
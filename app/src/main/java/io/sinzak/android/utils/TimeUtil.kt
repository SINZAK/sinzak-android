package io.sinzak.android.utils

import java.lang.Exception

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
                else ->
                    return "· ${timeGap.toInt()}시간 전"
            }

        } catch (e: Exception)
        {

        }
        return "· 최근"
    }
}
package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object TimeUtil {

    private const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val WRONG_FORMAT = "날짜 형식이 잘못되었습니다."

    private val UTC_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")
    private val KOREA_TIME_ZONE: TimeZone = TimeZone.getTimeZone("Asia/Seoul")

    private val utcFormat = SimpleDateFormat(UTC_DATE_FORMAT, Locale.KOREA).apply {
        timeZone = UTC_TIME_ZONE
    }
    private val localFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).apply {
        timeZone = KOREA_TIME_ZONE
    }

    // UTC -> Local
    fun convertUtcToLocal(utcTime: String): String {
        return try {
            val date = utcFormat.parse(utcTime)
            date?.let { localFormat.format(it) } ?: throw IllegalArgumentException(WRONG_FORMAT)
        } catch (e: Exception) {
            e.printStackTrace()
            WRONG_FORMAT
        }
    }

    // Local -> UTC
    fun convertLocalToUtc(localTime: String): String {
        return try {
            val date = localFormat.parse(localTime)
            date?.let { utcFormat.format(it) } ?: throw IllegalArgumentException(WRONG_FORMAT)
        } catch (e: Exception) {
            e.printStackTrace()
            WRONG_FORMAT
        }
    }

    // UTC -> Calendar
    fun convertUtcToCalendar(utcTime: String): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = utcFormat.parse(utcTime)
        return calendar
    }

    fun getDayDiff(later: String, early: String): Int {
        return try {
            val laterDate = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).parse(later)
            val earlyDate = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).parse(early)

            if (laterDate != null && earlyDate != null) {
                val diffInMillis = laterDate.time - earlyDate.time
                (diffInMillis / (24 * 60 * 60 * 1000)).toInt()
            } else {
                throw IllegalArgumentException(WRONG_FORMAT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun getDayDiffFromNow(utcFormatString: String): Int {
        return try {
            val localFormatString = convertUtcToLocal(utcFormatString)
            val nowString = localFormat.format(Date())
            getDayDiff(localFormatString, nowString)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun isExpired(expirationDate: String): Boolean {
        return try {
            val parsedExpirationDate = utcFormat.parse(expirationDate)
            parsedExpirationDate?.before(Date()) ?: true
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    // GregorianCalendar -> Local
    fun convertGregorianCalendarToLocal(calendar: GregorianCalendar): String {
        return SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).format(Date(calendar.timeInMillis))
    }

    // Local -> GregorianCalendar
    fun convertLocalToGregorianCalendar(localTime: String): GregorianCalendar {
        return try {
            GregorianCalendar(KOREA_TIME_ZONE).apply {
                time = localFormat.parse(localTime) ?: throw IllegalArgumentException(WRONG_FORMAT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalArgumentException(WRONG_FORMAT)
        }
    }

    /*** TODO : 일단 안쓰는 것들 @@@@@@@@@@@@@@@@@@@@@@@@@@@ ***/

    private fun getDayDiff(later: Long, early: Long): Int {
        val gap: Long = later - early
        val dayDiff: Float = (gap / (24f * 60 * 60 * 1000))
        return dayDiff.roundToInt()
    }

    private fun getDateInstanceFromLocalFormat(localFormatString: String): Date {
        val inputFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA)
        return inputFormat.parse(localFormatString)
            ?: throw IllegalArgumentException(WRONG_FORMAT)
    }

    private fun initHourAndBelow(gregorianCalendar: GregorianCalendar) {
        gregorianCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    //For Fake
    fun getCurrentTimeByServerFormat(): String =
        SimpleDateFormat(UTC_DATE_FORMAT, Locale.KOREA).format(Date())
}
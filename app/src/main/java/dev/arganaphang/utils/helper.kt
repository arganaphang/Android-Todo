package dev.arganaphang.utils

import android.text.format.DateUtils
import java.util.Date


fun ralativeDateFromNow(d: Date): String {
    return DateUtils.getRelativeTimeSpanString(d.time, Date().time, 0L, DateUtils.FORMAT_ABBREV_ALL).toString()
}
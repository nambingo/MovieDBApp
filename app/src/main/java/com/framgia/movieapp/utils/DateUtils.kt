package com.framgia.movieapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */

object DateUtils {
  fun convertDate(date: String?): String {
    var format = SimpleDateFormat("yyyy-MM-dd")
    var newDate: Date? = null
    try {
      if (date != null) newDate = format.parse(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    format = SimpleDateFormat("MM/dd/yyyy")
    val date2 = format.format(newDate)
    return date2
  }
}

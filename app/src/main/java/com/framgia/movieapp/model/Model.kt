package com.framgia.movieapp.model

import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */
class Movie {
  var results: ArrayList<MainResults> = ArrayList()
}

class NowPlaying {
  var results: ArrayList<MainResults> = ArrayList()
}

class MovieGenre {
  var genres: ArrayList<Genres> = ArrayList()
}

class Genres {
  var id: String = ""
  var name: String = ""
}

class MainResults : Serializable {
  var id: String = ""
  var title: String = ""
  var poster_path: String = ""
  var overview: String = ""
  var vote_average: String = ""
  var release_date: String = ""
  var backdrop_path: String = ""
  private var count: Int = 0
  var isFavorite = false

  override fun equals(obj: Any?): Boolean {
    if (obj === this) {
      count = 1
      return true
    } else if (obj is MainResults) {
      if (obj.title.equals(title, ignoreCase = true)) {
        count++
        return true
      }
    }
    return false
  }

  companion object {

    fun convertTime(time1: String): Date? {
      val sdf = SimpleDateFormat("yyyy-MM-dd")
      try {
        val parsedDate = sdf.parse(time1)
        return parsedDate
      } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
        return null
      }

    }
  }

}
package com.framgia.movieapp.services

import com.framgia.movieapp.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by FRAMGIA\pham.duc.nam on 26/06/2017.
 */

object Services {
  private val rsMain = Retrofit.Builder().baseUrl(Constant.URL_BASE)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  fun loadNowPlaying(page: Int,
      iServiceNowPlaying: ServiceNowPlaying.IServiceNowPlaying) {
    ServiceNowPlaying(iServiceNowPlaying, rsMain, page)
  }

  fun loadDetail(id: String, page: Int,
      iServiceDetail: ServiceDetail.IServiceDetail) {
    ServiceDetail(iServiceDetail, rsMain, id, page)
  }

  interface IServiceListener {
    fun onError(error: String)
  }
}

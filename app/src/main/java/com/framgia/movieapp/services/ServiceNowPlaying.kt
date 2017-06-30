package com.framgia.movieapp.services

import com.framgia.movieapp.model.NowPlaying
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.utils.Constant
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by FRAMGIA\pham.duc.nam on 26/06/2017.
 */

class ServiceNowPlaying(internal var mIServiceNowPlaying: ServiceNowPlaying.IServiceNowPlaying,
    retrofit: Retrofit, page: Int) : Callback<NowPlaying> {

  init {
    val iLoadNow = retrofit.create(ILoadNow::class.java)
    val call = iLoadNow.loadNow(Constant.API_KEY, page)
    call.enqueue(this)
  }

  override fun onResponse(call: Call<NowPlaying>, response: Response<NowPlaying>) {
    mIServiceNowPlaying.onSuccess(response.body().results)
  }

  override fun onFailure(call: Call<NowPlaying>, t: Throwable) {
    mIServiceNowPlaying.onError(t.message!!)
  }

  interface ILoadNow {
    @GET("movie/now_playing")
    fun loadNow(@Query("api_key") api_key: String, @Query("page") page: Int): Call<NowPlaying>
  }

  interface IServiceNowPlaying : Services.IServiceListener {
    fun onSuccess(mainResultsList: ArrayList<MainResults>)
  }
}

package com.framgia.movieapp.services

import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.model.Movie
import com.framgia.movieapp.utils.Constant
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by FRAMGIA\pham.duc.nam on 28/06/2017.
 */

class ServiceDetail(internal var mIServiceDetail: ServiceDetail.IServiceDetail, retrofit: Retrofit,
    id: String,
    page: Int) : Callback<Movie> {

  init {
    val iLoadDetail = retrofit.create(ServiceDetail.ILoadDetail::class.java)
    val call = iLoadDetail.loadDetail(id, Constant.API_KEY, page)
    call.enqueue(this)
  }

  override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
    mIServiceDetail.onSuccess(response.body().results)
  }

  override fun onFailure(call: Call<Movie>, t: Throwable) {
    mIServiceDetail.onError(t.message!!)
  }

  interface ILoadDetail {
    @GET("movie/{id}/similar")
    fun loadDetail(@Path("id") id: String, @Query("api_key") api_key: String,
        @Query("page") page: Int): Call<Movie>
  }

  interface IServiceDetail : Services.IServiceListener {
    fun onSuccess(detailResultsList: ArrayList<MainResults>)
  }
}

package com.framgia.movieapp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.framgia.movieapp.base.BaseActivity
import com.framgia.movieapp.detail.DetailAdapter
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.services.ServiceDetail
import com.framgia.movieapp.services.Services
import com.framgia.movieapp.utils.Constant
import com.framgia.movieapp.utils.PreferencesUtils
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.ArrayList

/**
 * Created by FRAMGIA\pham.duc.nam on 28/06/2017.
 */

class DetailActivity : BaseActivity(), DetailAdapter.OnClickStarListener {


  internal var mMainResults: MainResults = MainResults()
  internal var mPage = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    showLoading()
    setUpRecyclerView()
    getContent()
    getData(mPage)
  }

  private fun setUpRecyclerView() {
    mRecyclerView.layoutManager = LinearLayoutManager(this)
    mRecyclerView.isNestedScrollingEnabled = false
    mRecyclerView.adapter = DetailAdapter(this)
    (mRecyclerView.adapter as DetailAdapter).setOnClickStarListener(this)
  }

  private fun getContent() {
    mMainResults = intent.getSerializableExtra(Constant.TAG_MAIN_RESULT) as MainResults
    setImage(mMainResults.backdrop_path, imgBackdrop)
    tvTitle.text = mMainResults.title
    var rate = mMainResults.vote_average
    if (rate.length > 3) {
      rate = rate.substring(0, 3)
    }
    tvRate.text = rate
    tvOverview.text = mMainResults.overview
    if (mMainResults.isFavorite) {
      imgHeartFill.visibility = View.VISIBLE or View.GONE
      imgHeart.visibility = View.GONE or View.VISIBLE
    }
  }

  private fun getData(page: Int) {
    Services.loadDetail(mMainResults.id, page, object : ServiceDetail.IServiceDetail {
      override fun onSuccess(detailResultsList: ArrayList<MainResults>) {
        hideLoading()
        if (PreferencesUtils.getFavoriteResult(applicationContext) != null) {
          checkList(detailResultsList)
        } else {
          setData(detailResultsList)
        }
      }

      override fun onError(error: String) {}
    })
  }

  private fun setData(list: ArrayList<MainResults>) {
    (mRecyclerView.adapter as DetailAdapter).clearData()
    (mRecyclerView.adapter as DetailAdapter).setData(list)
  }

  private fun setImage(url: String, imgView: ImageView) {
    Glide.with(this).load(Constant.URL_IMAGE + url).crossFade().into(imgView)
  }

  override fun onClick(result: MainResults) {
    PreferencesUtils.setFavoriteResult(result, applicationContext)
  }

  private fun checkList(list: ArrayList<MainResults>) {
    for (i in list.indices) {
      val listID = list[i].id
      for (j in 0..PreferencesUtils.getFavoriteResult(applicationContext).size - 1) {
        val listFaveID = PreferencesUtils.getFavoriteResult(applicationContext)[j].id
        if (listID != listFaveID) {
          continue
        }
        list[i].isFavorite = true
      }
    }
    setData(list)
  }
}

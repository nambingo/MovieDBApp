package com.framgia.movieapp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.framgia.movieapp.base.BaseActivity
import com.framgia.movieapp.detail.DetailAdapter
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.services.ServiceDetail
import com.framgia.movieapp.services.Services
import com.framgia.movieapp.utils.Constant
import com.framgia.movieapp.utils.DateUtils.convertDate
import com.framgia.movieapp.utils.InfiniteScrollListener
import com.framgia.movieapp.utils.PreferencesUtils
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.ArrayList

/**
 * Created by FRAMGIA\pham.duc.nam on 30/06/2017.
 */

class DetailActivity : BaseActivity(), DetailAdapter.OnClickStarListener {
  internal var mMainResults: MainResults = MainResults()
  internal var mPage = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    mRecyclerView.apply {
      setHasFixedSize(true)
      isNestedScrollingEnabled = false
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
    }
    initAdapter();
    show()
    getContent()
  }

  private fun initAdapter() {
    if (mRecyclerView.adapter != null) return
    mRecyclerView.adapter = DetailAdapter(this)
    (mRecyclerView.adapter as DetailAdapter).setOnClickStarListener(this)
  }

  private fun getContent() {
    mMainResults = intent.getSerializableExtra(Constant.TAG_MAIN_RESULT) as MainResults
    setImage(mMainResults.backdrop_path, imgBackdrop)
    tvTitle.text = mMainResults.title
    toolbar.title = mMainResults.title
    var rate = mMainResults.vote_average
    if (rate.length > 3) {
      rate = rate.substring(0, 3)
    }
    tvRate.text = rate
    tvOverview.text = mMainResults.overview
    tvDate.text = convertDate(mMainResults.release_date)

    if (mMainResults.isFavorite){
      imgHeartFill.visibility = View.VISIBLE
      imgHeart.visibility = View.GONE
    }else{
      imgHeartFill.visibility = View.GONE
      imgHeart.visibility = View.VISIBLE
    }

    imgHeart.setOnClickListener {
      imgHeartFill.visibility = View.VISIBLE
      imgHeart.visibility = View.GONE
      PreferencesUtils.setFavoriteResult(mMainResults, this)

    }
  }

  override fun onResume() {
    super.onResume()
    getData(mPage)
  }

  private fun getData(page: Int) {
    Services.loadDetail(mMainResults.id, page, object : ServiceDetail.IServiceDetail {
      override fun onSuccess(detailResultsList: ArrayList<MainResults>) {
        if (PreferencesUtils.getFavoriteResult(this@DetailActivity) == null) {
          setData(detailResultsList)
        } else
          checkResultList(detailResultsList)
      }

      override fun onError(error: String) {
        hideLoading()
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    })
  }

  private fun setData(list: ArrayList<MainResults>) {
    if (mPage != 1) {
      addData(list)
    } else {
      clearAndAddData(list)
    }
  }

  private fun clearAndAddData(list: ArrayList<MainResults>) {
    (mRecyclerView.adapter as DetailAdapter).clearData()
    (mRecyclerView.adapter as DetailAdapter).setData(list)
    hide()
  }

  private fun addData(list: ArrayList<MainResults>) {
    (mRecyclerView.adapter as DetailAdapter).addData(list)
    hide()
  }

  private fun checkResultList(list: ArrayList<MainResults>) {
    for (i in list.indices) {
      val mResultListID = list[i].id
      for (j in PreferencesUtils.getFavoriteResult(this@DetailActivity).indices) {
        val mFavoriteListID = PreferencesUtils.getFavoriteResult(this@DetailActivity)[j].id
        if (mFavoriteListID != mResultListID) continue
        list[i].isFavorite = true
      }
    }
    setData(list)
  }

  private fun setImage(url: String, imgView: ImageView) {
    Glide.with(this).load(Constant.URL_IMAGE + url).crossFade().into(imgView)
  }

  override fun onClick(result: MainResults) {
    PreferencesUtils.setFavoriteResult(result, this)
  }
}

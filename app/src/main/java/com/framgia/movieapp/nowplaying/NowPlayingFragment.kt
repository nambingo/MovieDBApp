package com.framgia.movieapp.nowplaying

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.movieapp.R
import com.framgia.movieapp.base.BaseFragment
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.nowplaying.NowPlayingAdapter.OnClickStarListener
import com.framgia.movieapp.services.ServiceNowPlaying
import com.framgia.movieapp.services.Services
import com.framgia.movieapp.utils.InfiniteScrollListener
import com.framgia.movieapp.utils.PreferencesUtils
import kotlinx.android.synthetic.main.fragment_now_playing.*

/**
 * Created by FRAMGIA\pham.duc.nam on 23/06/2017.
 */

class NowPlayingFragment : BaseFragment(), OnClickStarListener {

  private var mPage = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_now_playing, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mRecyclerView.apply {
      setHasFixedSize(true)
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
      clearOnScrollListeners()
      addOnScrollListener(InfiniteScrollListener({ onLoadMore() }, linearLayout))
    }
    initAdapter();
    showLoading()
    getData(mPage)
  }

  private fun getData(page: Int) {
    Services.loadNowPlaying(page, object : ServiceNowPlaying.IServiceNowPlaying {
      override fun onSuccess(mainResultsList: ArrayList<MainResults>) {
        hideLoading()
        if (PreferencesUtils.getFavoriteResult(activity) == null) {
          clearAndAddData(mainResultsList)
        } else {
          checkResultList(mainResultsList)
        }
      }

      override fun onError(error: String) {}
    })
  }

  private fun checkResultList(list: ArrayList<MainResults>) {
    for (i in list.indices) {
      val mResultListID = list[i].id
      for (j in PreferencesUtils.getFavoriteResult(activity).indices) {
        val mFavoriteListID = PreferencesUtils.getFavoriteResult(activity)[j].id
        if (mFavoriteListID != mResultListID) continue
        list[i].isFavorite = true
      }
    }
    if (mPage != 1) {
      (mRecyclerView.adapter as NowPlayingAdapter).setLoaded()
      addData(list)
    } else {
      clearAndAddData(list)
    }

  }

  private fun clearAndAddData(list: ArrayList<MainResults>) {
    (mRecyclerView.adapter as NowPlayingAdapter).clearData()
    (mRecyclerView.adapter as NowPlayingAdapter).setData(list)
  }

  private fun addData(list: ArrayList<MainResults>) {
    (mRecyclerView.adapter as NowPlayingAdapter).addData(list)
  }

  private fun initAdapter() {
    if (mRecyclerView.adapter != null) return
    mRecyclerView.adapter = NowPlayingAdapter(activity)
    (mRecyclerView.adapter as NowPlayingAdapter).setOnClickStarListener(this)

  }

  private fun onLoadMore() {
    mPage++;
    getData(mPage)
  }

  override fun onClick(result: MainResults) {
    PreferencesUtils.setFavoriteResult(result,activity)
  }
}

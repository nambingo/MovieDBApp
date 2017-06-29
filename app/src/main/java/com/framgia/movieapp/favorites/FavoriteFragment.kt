package com.framgia.movieapp.favorites

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.movieapp.R
import com.framgia.movieapp.base.BaseFragment
import com.framgia.movieapp.utils.PreferencesUtils
import kotlinx.android.synthetic.main.fragment_favorites.*

/**
 * Created by FRAMGIA\pham.duc.nam on 23/06/2017.
 */

class FavoriteFragment : BaseFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {


    return inflater!!.inflate(R.layout.fragment_favorites, container, false)
  }

  private fun setupView() {
    mRecyclerView.apply {
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
    }
    if (mRecyclerView.adapter == null) {
      mRecyclerView.adapter = FavoritesAdapter(activity)
    }
  }

  private fun setData() {
    if (PreferencesUtils.getFavoriteResult(activity) == null) {
      return
    }
    setupView()
    (mRecyclerView.adapter as FavoritesAdapter).clearData()
    (mRecyclerView.adapter as FavoritesAdapter).setData(PreferencesUtils.getFavoriteResult(activity))
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser) {
      setData()
    }
  }
}

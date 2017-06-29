package com.framgia.movieapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.framgia.movieapp.favorites.FavoriteFragment
import com.framgia.movieapp.nowplaying.NowPlayingFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupViewPager()
    tabs.setupWithViewPager(viewpager)

  }

  private fun setupViewPager() {
    val adapter = ViewPagerAdapter(supportFragmentManager)
    adapter.addFrag(NowPlayingFragment(), getString(R.string.now_playing))
    adapter.addFrag(FavoriteFragment(), getString(R.string.favorites))
    viewpager.adapter = adapter
  }

  internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
      return mFragmentList[position]
    }

    override fun getCount(): Int {
      return mFragmentList.size
    }

    fun addFrag(fragment: Fragment, title: String) {
      mFragmentList.add(fragment)
      mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
      return mFragmentTitleList[position]
    }
  }
}

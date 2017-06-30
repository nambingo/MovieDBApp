package com.framgia.movieapp.favorites

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.framgia.movieapp.DetailActivity
import com.framgia.movieapp.R
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.utils.Constant
import kotlinx.android.synthetic.main.item_favorites.view.*
import java.util.*

/**
 * Created by FRAMGIA\pham.duc.nam on 26/06/2017.
 */

class FavoritesAdapter(
    private val context: Context) : RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {
  private var mMainResultsSelected = ArrayList<MainResults>()

  fun clearData() {
    this.mMainResultsSelected.clear()
    this.notifyDataSetChanged()
  }

  fun setData(list: ArrayList<MainResults>) {
    this.mMainResultsSelected = list
    this.notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): FavoritesAdapter.ItemViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_favorites, parent, false)
    val itemViewHolder = FavoritesAdapter.ItemViewHolder(view)
    return itemViewHolder
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    val mainResults = mMainResultsSelected[position]
    holder.bindData(mainResults)
  }

  override fun getItemCount(): Int {
    return mMainResultsSelected.size
  }

  class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mainResults: MainResults) {
      with(mainResults) {
        itemView.tvTitle.text = mainResults.title
        itemView.tvOverview.text = mainResults.overview
        itemView.tvRate.text = mainResults.vote_average
        itemView.tvDate.text = mainResults.release_date
        Glide.with(itemView.context)
            .load(Constant.URL_IMAGE + mainResults.poster_path)
            .crossFade()
            .into(itemView.imgPoster)

        itemView.tvMore.setOnClickListener {
          val intent = Intent(itemView.context, DetailActivity::class.java)
          intent.putExtra(Constant.TAG_MAIN_RESULT, mainResults)
          itemView.context.startActivity(intent)
        }

      }
    }
  }
}

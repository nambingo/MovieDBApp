package com.framgia.movieapp.detail

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.framgia.movieapp.DetailActivity
import com.framgia.movieapp.R
import com.framgia.movieapp.model.MainResults
import com.framgia.movieapp.utils.Constant
import java.util.ArrayList

import com.framgia.movieapp.utils.DateUtils.convertDate
import kotlinx.android.synthetic.main.item_movie_detail.view.*

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */

class DetailAdapter(
    private val context: Context) : RecyclerView.Adapter<DetailAdapter.ItemViewHolder>() {
  private var mDetailResultsSelected: ArrayList<MainResults> = ArrayList()
  private var onClickStarListener: OnClickStarListener? = null

  fun clearData() {
    this.mDetailResultsSelected.clear()
    this.notifyDataSetChanged()
  }

  fun setData(list: ArrayList<MainResults>) {
    this.mDetailResultsSelected = list
    this.notifyDataSetChanged()
  }

  fun setOnClickStarListener(onClick: OnClickStarListener) {
    this.onClickStarListener = onClick
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ItemViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie_detail, parent, false)
    val itemViewHolder = ItemViewHolder(view)
    return itemViewHolder
  }

  override fun onBindViewHolder(holder: DetailAdapter.ItemViewHolder, position: Int) {
    val detailResults = mDetailResultsSelected[position]
    holder.bindData(detailResults)
  }

  override fun getItemCount(): Int {
    return mDetailResultsSelected.size
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(mainResults: MainResults) {
      with(mainResults) {
        itemView.tvTitle.text = mainResults.title
        itemView.tvOverview.text = mainResults.overview
        var rate = mainResults.vote_average
        if (rate.length > 3) {
          rate = rate.substring(0, 3)
        }
        itemView.tvRate.text = rate
        itemView.tvDate.text = convertDate(mainResults.release_date)
        Glide.with(itemView.context)
            .load(Constant.URL_IMAGE + mainResults.poster_path)
            .crossFade()
            .into(itemView.imgPoster)

        if (mainResults.isFavorite) {
          itemView.imgHeartFill.visibility = View.VISIBLE or View.GONE
          itemView.imgHeart.visibility = View.GONE or View.VISIBLE
        }

        itemView.imgHeart.setOnClickListener {
          itemView.imgHeart.visibility = View.GONE
          itemView.imgHeartFill.visibility = View.VISIBLE
          if (onClickStarListener == null) return@setOnClickListener
          if (mainResults.isFavorite) return@setOnClickListener
          mainResults.isFavorite = true
          onClickStarListener!!.onClick(mainResults)
        }

        itemView.tvMore.setOnClickListener {
          val intent = Intent(itemView.context, DetailActivity::class.java)
          intent.putExtra(Constant.TAG_MAIN_RESULT, mainResults)
          itemView.context.startActivity(intent)
        }
      }
    }
  }

  interface OnClickStarListener {
    fun onClick(result: MainResults)
  }
}

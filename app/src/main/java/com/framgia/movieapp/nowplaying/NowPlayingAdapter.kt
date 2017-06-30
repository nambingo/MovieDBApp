package com.framgia.movieapp.nowplaying

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
import kotlinx.android.synthetic.main.item_now_playing.view.*
import java.util.*

/**
 * Created by FRAMGIA\pham.duc.nam on 26/06/2017.
 */

class NowPlayingAdapter(
    private val context: Context) : RecyclerView.Adapter<NowPlayingAdapter.ItemViewHolder>() {

  private var mMainResultsList: ArrayList<MainResults> = ArrayList()
  private var onClickStarListener: OnClickStarListener? = null

  fun setOnClickStarListener(onClick: OnClickStarListener) {
    this.onClickStarListener = onClick
  }

  fun clearData() {
    mMainResultsList.clear()
    this.notifyDataSetChanged()
  }

  fun setData(list: ArrayList<MainResults>) {
    this.mMainResultsList = list
    this.notifyDataSetChanged()
  }

  fun addData(list: ArrayList<MainResults>) {
    val initPos = mMainResultsList.size - 1
    mMainResultsList.removeAt(initPos)
    notifyItemRemoved(initPos)

    mMainResultsList.addAll(list)
    notifyItemRangeChanged(initPos, mMainResultsList.size)
  }

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): NowPlayingAdapter.ItemViewHolder? {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_now_playing, parent, false)
    val itemViewHolder = ItemViewHolder(view)
    return itemViewHolder
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    val mainResults = mMainResultsList[position]
    holder.bindData(mainResults)
  }

  override fun getItemCount(): Int {
    return mMainResultsList.size
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

        if (mainResults.isFavorite){
          itemView.imgHeartFill.visibility = View.VISIBLE
          itemView.imgHeart.visibility = View.GONE
        }else{
          itemView.imgHeartFill.visibility = View.GONE
          itemView.imgHeart.visibility = View.VISIBLE
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
          val intent = Intent(context, DetailActivity::class.java)
          intent.putExtra(Constant.TAG_MAIN_RESULT, mainResults)
          context.startActivity(intent)
        }
      }
    }
  }

  interface OnClickStarListener {
    fun onClick(result: MainResults)
  }
}

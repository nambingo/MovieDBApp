package com.framgia.movieapp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import com.framgia.movieapp.utils.ProgressDialogUtil

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */

open class BaseActivity : AppCompatActivity() {
  protected var mProgress: ProgressDialogUtil? = null

  override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
    return super.onCreateView(name, context, attrs)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mProgress = ProgressDialogUtil(this)
  }

  fun showLoading() {
    if (mProgress != null && !mProgress!!.isShowing) {
      mProgress = mProgress!!.show(false)
    }
  }

  fun hideLoading() {
    if (mProgress != null && mProgress!!.isShowing) {
      mProgress!!.dismiss()
    }
  }
}

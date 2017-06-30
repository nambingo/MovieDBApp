package com.framgia.movieapp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.movieapp.utils.ProgressDialogUtil
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */

open class BaseFragment : Fragment() {
  protected var mProgress: ProgressDialogUtil? = null
  private var hud: KProgressHUD? = null

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mProgress = ProgressDialogUtil(activity)
    hud = KProgressHUD.create(activity)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);

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

  fun show(){
    hud?.show()
  }
  fun hide(){
    hud?.dismiss()
  }
}

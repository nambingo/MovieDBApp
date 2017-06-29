package com.framgia.movieapp.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.framgia.movieapp.R
import java.util.Timer

/**
 * Created by FRAMGIA\pham.duc.nam on 29/06/2017.
 */

class ProgressDialogUtil : Dialog {
  internal var mContext: Context

  constructor(context: Context) : super(context) {
    this.mContext = context
  }

  constructor(context: Context, themeResId: Int) : super(context, themeResId) {
    this.mContext = context
  }


  fun show(
      cancelable: Boolean): ProgressDialogUtil {

    val dialog = ProgressDialogUtil(mContext, R.style.CustomProgress)

    dialog.setTitle("")
    dialog.setContentView(R.layout.progress_dialog)
    dialog.setCancelable(cancelable)
    dialog.window!!.attributes.gravity = Gravity.CENTER
    val lp = dialog.window!!.attributes
    lp.dimAmount = 0.2f
    dialog.window!!.attributes = lp

    dialog.show()
    return dialog
  }

  companion object {
    private val mTimer = Timer()
  }
}

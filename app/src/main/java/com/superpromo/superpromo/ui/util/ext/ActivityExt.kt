package com.superpromo.superpromo.ui.util.ext

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.superpromo.superpromo.R


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun setStatusBarGradiant(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = activity.window
        val background = ContextCompat.getDrawable(activity, R.drawable.gradient_toolbar)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(activity, R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(activity, R.color.black)
        window.setBackgroundDrawable(background)
    }
}
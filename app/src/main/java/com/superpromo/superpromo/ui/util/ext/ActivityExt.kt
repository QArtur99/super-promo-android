package com.superpromo.superpromo.ui.util.ext

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import com.superpromo.superpromo.BuildConfig
import com.superpromo.superpromo.R

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun setStatusBarGradient(activity: Activity) {
    val window: Window = activity.window
    val background = ContextCompat.getDrawable(activity, R.drawable.gradient_toolbar)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(activity, R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(activity, R.color.black)
    window.setBackgroundDrawable(background)
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun setStatusBarBlack(activity: Activity) {
    val window: Window = activity.window
    val background = ContextCompat.getDrawable(activity, R.color.black)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(activity, R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(activity, R.color.black)
    window.setBackgroundDrawable(background)
}

fun Activity.setToolbar(toolbar: Toolbar) {
    setActionBar(toolbar)
    title = ""
    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    toolbar.setNavigationOnClickListener {
        if (hideSoftKeyBoard(it) == false) {
            onBackPressed()
        }
    }
}

fun Activity.shareApp() {
    try {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_check_out) + getString(R.string.google_play_url_id) + BuildConfig.APPLICATION_ID
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    } catch (ex: ActivityNotFoundException) {
        snackbarLong(R.string.common_intent_text_error)
    }
}

fun Activity.contactUs() {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:" + BuildConfig.CONTACT_EMAIL)
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact_us_feedback))
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        snackbarLong(R.string.common_intent_email_error)
    }
}

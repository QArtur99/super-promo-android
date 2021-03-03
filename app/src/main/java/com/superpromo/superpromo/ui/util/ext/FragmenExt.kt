package com.superpromo.superpromo.ui.util.ext

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.util.assistant.BackPress


const val KEY_NAV_BACK_STACK = "KEY_NAV_BACKSTACK"

fun Fragment.onNavBackStackListener(onResult: (bundle: Bundle) -> Unit) {
    findNavController().currentBackStackEntry?.savedStateHandle
        ?.getLiveData<Bundle>(KEY_NAV_BACK_STACK)
        ?.observe(viewLifecycleOwner) { result ->
            onResult(result)
        }
}

fun Fragment.setNavigationResult(result: Bundle) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(KEY_NAV_BACK_STACK, result)
    findNavController().popBackStack()
}

fun Fragment.safeNavigate(resId: Int, args: Bundle? = null) {
    try {
        findNavController().navigate(resId, args)
    } catch (e: Exception) {

    }
}

fun Fragment.addBackPressListener(backPress: BackPress) {
    activity?.onBackPressedDispatcher?.addCallback {
        backPress.onBackPressed()
        isEnabled = false
        activity?.onBackPressed()
    }
}

fun Fragment.setToolbarHome(toolbar: Toolbar) {
    val activityCompat = activity as AppCompatActivity
    activityCompat.setSupportActionBar(toolbar)
    activityCompat.title = ""
    toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
}

fun Fragment.setToolbar(toolbar: Toolbar) {
    val activityCompat = activity as AppCompatActivity
    activityCompat.setSupportActionBar(toolbar)
    activityCompat.title = ""
    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    toolbar.setNavigationOnClickListener {
        if (activity?.hideSoftKeyBoard(it) == false) {
            activityCompat.onBackPressed()
        }
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Fragment.setStatusBarDark() {
    val activity = activity ?: return
    val window: Window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(activity, R.color.black)
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@MainThread
fun Fragment.setStatusBarTransparent() {
    val activity = activity ?: return
    val window: Window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(activity, R.color.transparent)
}

fun Fragment.toast(text: String) {
    Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
}

fun Fragment.snackbar(view: View, text: Any) {
    val msg = when (text) {
        is String -> text
        is Int -> getString(text)
        else -> ""
    }
    val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
    snackbar.show()
}
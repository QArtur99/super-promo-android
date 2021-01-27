package com.superpromo.superpromo.ui.util.ext

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

fun Fragment.addBackPressListener(backPress: BackPress) {
    activity?.onBackPressedDispatcher?.addCallback {
        findNavController().popBackStack()
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
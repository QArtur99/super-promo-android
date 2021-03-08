package com.superpromo.superpromo.ui.util.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Activity.toastShort(text: Any) = toast(this, text, Toast.LENGTH_SHORT)
fun Activity.toastLong(text: Any) = toast(this, text, Toast.LENGTH_LONG)

fun Fragment.toastShort(text: Any) = toast(requireActivity(), text, Toast.LENGTH_SHORT)
fun Fragment.toastLong(text: Any) = toast(requireActivity(), text, Toast.LENGTH_LONG)

fun toast(context: Context, text: Any, duration: Int) {
    val msg = when (text) {
        is String -> text
        is Int -> context.getString(text)
        else -> ""
    }
    Toast.makeText(context, msg, duration).show()
}
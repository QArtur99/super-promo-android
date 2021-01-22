package com.superpromo.superpromo.ui.util.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


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
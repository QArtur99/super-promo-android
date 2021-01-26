package com.superpromo.superpromo.ui.util

import java.text.Normalizer

private val REGEX_UNACCENT = "[^\\p{ASCII}]".toRegex()

fun CharSequence.unaccent(): String {
    var temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    temp = temp.replace("[Łł]".toRegex(), "l")
    temp = temp.replace("[Øø]".toRegex(), "o")
    return REGEX_UNACCENT.replace(temp, "")
}
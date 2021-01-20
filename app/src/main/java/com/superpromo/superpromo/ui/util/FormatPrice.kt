package com.superpromo.superpromo.ui.util

object FormatPrice {
    fun getCurrency(price: Double?, currency: String?) = "%.2f %s".format(price, currency)
}
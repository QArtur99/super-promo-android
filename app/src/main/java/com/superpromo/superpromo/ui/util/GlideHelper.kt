package com.superpromo.superpromo.ui.util

import android.widget.ImageView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R

object GlideHelper {
    fun bingImg(imageView: ImageView, glide: GlideRequests, imgUrl: String) {
        if (imgUrl.startsWith("http")) {
            glide.load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_broken_image_24)
        }
    }
}

package com.superpromo.superpromo.ui.compare.adapter.suggestion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superpromo.superpromo.GlideRequests
import com.superpromo.superpromo.R
import com.superpromo.superpromo.data.network.model.Suggestion

class SuggestionViewHolder constructor(
    private val view: View,
    private val glide: GlideRequests,
    private val clickListener: SuggestionListAdapter.OnClickListener
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
            glide: GlideRequests,
            clickListener: SuggestionListAdapter.OnClickListener
        ): SuggestionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_suggestion, parent, false)
            return SuggestionViewHolder(view, glide, clickListener)
        }
    }


    private val suggestion: TextView = view.findViewById(R.id.suggestion)
    private var item: Suggestion? = null

    init {
        view.setOnClickListener {
            item?.let { clickListener.onClick(view, it) }
        }
    }

    fun bind(item: Suggestion?) {
        this.item = item
        suggestion.text = item?.name
    }
}
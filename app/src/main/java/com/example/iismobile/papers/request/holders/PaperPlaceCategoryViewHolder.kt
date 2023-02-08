package com.example.iismobile.papers.request.holders

import android.view.View
import android.widget.TextView
import com.example.iismobile.R
import com.example.iismobile.common.adapters.ChoiceViewHolder
import com.example.iismobile.common.data.PaperPlaceCategory

class PaperPlaceCategoryViewHolder(itemView: View) : ChoiceViewHolder<PaperPlaceCategory>(itemView) {

    override fun bind(data: PaperPlaceCategory, isFirst: Boolean, isLast: Boolean, isSelected: Boolean) = with(itemView) {
        findViewById<TextView>(R.id.choose_item_label).let {
            it.isSelected = isSelected
            it.text = data.type
        }
        findViewById<View>(R.id.choose_item_container).let {
            it.isSelected = isSelected
            it.setBackgroundResource(when {
                isFirst && !isLast -> R.drawable.b_left_edge
                isLast && !isFirst -> R.drawable.b_right_edge
                isLast && isFirst -> R.drawable.b_left_right_edge
                else -> R.drawable.b_none_edge
            })
        }
    }

}
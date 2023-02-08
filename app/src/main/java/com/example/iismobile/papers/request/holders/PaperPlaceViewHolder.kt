package com.example.iismobile.papers.request.holders

import android.view.View
import android.widget.TextView
import com.example.iismobile.R
import com.example.iismobile.common.adapters.ChoiceViewHolder
import com.example.iismobile.common.data.PaperPlace

class PaperPlaceViewHolder(itemView: View) : ChoiceViewHolder<PaperPlace>(itemView) {

    override fun bind(data: PaperPlace, isFirst: Boolean, isLast: Boolean, isSelected: Boolean) = with(itemView) {
        findViewById<TextView>(R.id.choose_item_label).let {
            it.isSelected = isSelected
            it.text = data.name
        }
        findViewById<View>(R.id.choose_item_container).let {
            it.isSelected = isSelected
            it.setBackgroundResource(R.drawable.b_left_right_edge)
        }
    }


}
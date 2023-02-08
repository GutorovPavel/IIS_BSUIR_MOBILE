package com.example.iismobile.papers.holders

import android.view.View
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.common.data.Paper
import com.example.iismobile.common.utils.asVisibility
import kotlinx.android.synthetic.main.i_paper.view.*

class PaperViewHolder(itemView: View) : SimpleViewHolder<Paper>(itemView) {
    override fun bind(data: Paper, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        title.text = data.provisionPlace
        date.text = data.dateOrder
        seal.visibility = (data.status() == Paper.Status.PRINTED).asVisibility()
        status.text = data.status().description
    }
}
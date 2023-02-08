package com.example.iismobile.marksheets.holders

import android.view.View
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.common.utils.asVisibility
import com.example.iismobile.common.data.MarkSheet
import kotlinx.android.synthetic.main.i_sheet.view.*

class MarkSheetViewHolder(itemView: View): SimpleViewHolder<MarkSheet>(itemView) {
    override fun bind(data: MarkSheet, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        title.text = data.subject.name
        date.text = "${data.term} курс"
        type.text = data.subject.lessonType
        status.text = data.statusString
        cost.text = String.format("%.2fр", data.price())

        seal.visibility = (data.status() == MarkSheet.Status.PRINTED).asVisibility()
    }
}
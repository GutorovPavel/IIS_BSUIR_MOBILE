package com.example.iismobile.profile.holders

import android.view.View
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.common.data.Reference
import kotlinx.android.synthetic.main.i_reference.view.*

class ReferenceViewHolder(itemView: View) : SimpleViewHolder<Reference>(itemView) {

    override fun bind(data: Reference, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        url.text = data.reference
        title.text = data.name
    }
}
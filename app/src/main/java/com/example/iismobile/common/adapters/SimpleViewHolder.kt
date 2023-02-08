package com.example.iismobile.common.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleViewHolder<T>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(data: T, isFirst: Boolean, isLast: Boolean)

}
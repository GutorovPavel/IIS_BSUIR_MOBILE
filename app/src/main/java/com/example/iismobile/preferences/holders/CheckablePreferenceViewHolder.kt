package com.example.iismobile.preferences.holders

import android.view.View
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.preferences.data.PreferenceEntry
import kotlinx.android.synthetic.main.i_checkable_preference.view.*

class CheckablePreferenceViewHolder(itemView: View) : SimpleViewHolder<PreferenceEntry>(itemView) {
    override fun bind(data: PreferenceEntry, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        preference_name.setText(data.name)

        preference_image.setImageResource(if (data.value) data.activeIcon else data.inactiveIcon)
    }

}
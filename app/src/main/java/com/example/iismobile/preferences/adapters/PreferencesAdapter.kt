package com.example.iismobile.preferences.adapters

import com.example.iismobile.R
import com.example.iismobile.common.adapters.SimpleAdapter
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.preferences.data.PreferenceEntry
import com.example.iismobile.preferences.data.PreferenceType
import com.example.iismobile.preferences.holders.CheckablePreferenceViewHolder


class PreferencesAdapter(
    private val onPreferenceClick: (PreferenceEntry) -> Unit
) : SimpleAdapter<PreferenceEntry, CheckablePreferenceViewHolder>(
    R.layout.i_checkable_preference, ::CheckablePreferenceViewHolder
) {

    fun toggleByType(type: PreferenceType) {
        val index = list.indexOfFirst { it.type == type }
        if (index < 0)
            return
        list = list.map { if (it.type == type) it.toggled() else it }
        notifyItemChanged(index)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<PreferenceEntry>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onPreferenceClick(list[position])
        }
    }

}
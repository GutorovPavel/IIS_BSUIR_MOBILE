package com.example.iismobile.profile.holders

import android.view.View
import com.example.iismobile.common.adapters.AddableViewHolder
import com.example.iismobile.common.data.Skill
import com.example.iismobile.common.utils.asVisibility
import kotlinx.android.synthetic.main.i_skill.view.*

class SkillsViewHolder(itemView: View): AddableViewHolder<Skill>(itemView) {

    fun setOnClickListener(listener:() -> Unit) = with(itemView) {
        value.setOnClickListener { listener() }
    }

    fun setRemovable(value: Boolean, onRemoveClick: (() -> Unit)? = null) = with(itemView) {
        remove.visibility = value.asVisibility()
        if (onRemoveClick != null)
            remove.setOnClickListener { onRemoveClick() }
        else remove.setOnClickListener(null)
    }

    override fun bindItem(data: Skill, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        value.text = data.name
    }

    override fun bindAdd(isFirst: Boolean, isLast: Boolean) = with(itemView) { }

}
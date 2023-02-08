package com.example.iismobile.profile.holders

import android.view.View
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.common.data.Skill
import kotlinx.android.synthetic.main.i_skill.view.*

class SkillSuggestionViewHolder(itemView: View) : SimpleViewHolder<Skill>(itemView) {

    override fun bind(data: Skill, isFirst: Boolean, isLast: Boolean) = with(itemView) {
        value.text = data.name
    }

}
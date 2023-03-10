package com.example.iismobile.group.members

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iismobile.common.data.GroupMember
import com.example.iismobile.common.data.SpecialGroupMember
import com.example.iismobile.common.utils.asVisibility
import kotlinx.android.synthetic.main.i_group_special_member.view.*

abstract class AbstractGroupMemberViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(member: GroupMember): Unit
}

class SpecialGroupMemberViewHolder(itemView: View): AbstractGroupMemberViewHolder(itemView) {
    override fun bind(member: GroupMember): Unit = with(itemView) {
        member as SpecialGroupMember

        member_name.text = member.fullName

        member_email.visibility = member.email.isNotEmpty().asVisibility()
        member_email.text = member.email

        member_phone.visibility = member.phone.isNotEmpty().asVisibility()
        member_phone.text = member.phone

        member_position.visibility = member.position.isNotEmpty().asVisibility()
        member_position.text = member.position

        Glide.with(context)
                .load(member.photoUrl)
                .into(member_image)
    }
}

class GroupMemberViewHolder(itemView: View): AbstractGroupMemberViewHolder(itemView) {
    override fun bind(member: GroupMember): Unit = with(itemView) {
        member_name.text = member.fullName

        member_email.visibility = member.email.isNotEmpty().asVisibility()
        member_email.text = member.email

        member_phone.visibility = member.phone.isNotEmpty().asVisibility()
        member_phone.text = member.phone

        member_position.visibility = member.position.isNotEmpty().asVisibility()
        member_position.text = member.position
    }
}
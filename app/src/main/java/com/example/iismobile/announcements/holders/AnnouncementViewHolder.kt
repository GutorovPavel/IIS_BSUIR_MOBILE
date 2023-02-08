package com.example.iismobile.announcements.holders

import android.view.View
import com.bumptech.glide.Glide
import com.example.iismobile.common.adapters.SimpleViewHolder
import com.example.iismobile.common.data.Announcement
import kotlinx.android.synthetic.main.i_announcement.view.*

class AnnouncementViewHolder(itemView: View) : SimpleViewHolder<Announcement>(itemView) {

    override fun bind(data: Announcement, isFirst: Boolean, isLast: Boolean): Unit = with(itemView) {
        ann_author.text = data.name
        ann_date.text = data.date
        ann_content.text = data.content


        Glide.with(context)
                .load(data.photoUrl ?: "https://iis.bsuir.by/assets/default-photo.gif")
                .into(ann_author_image)
    }

}
package com.example.iismobile.announcements

import com.example.iismobile.common.data.Announcement

interface IAnnouncementsModel {

    fun getAnnouncements(allowCache: Boolean): List<Announcement>?

}
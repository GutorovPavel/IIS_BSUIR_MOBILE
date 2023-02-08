package com.example.iismobile.announcements

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.data.Announcement

class AnnouncementsModel(
    api: ApiService,
    store: CredentialsStore,
    context: Context,
    cacheManager: ICacheManager
) : IAnnouncementsModel, ApiModel(api, store, context, cacheManager) {

    private var announcementsCache: List<Announcement>? = null

    override fun getAnnouncements(allowCache: Boolean): List<Announcement>? =
            apiCall(ApiService::announcements, if (allowCache) announcementsCache else null)
                    .also { announcementsCache = it }

}
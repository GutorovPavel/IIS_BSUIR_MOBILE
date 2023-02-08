package com.example.iismobile.group

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.data.GroupInfo

class GroupModel (
    api: ApiService,
    store: CredentialsStore,
    applicationContext: Context,
    cacheManager: ICacheManager,
): ApiModel(api, store, applicationContext, cacheManager), IGroupModel {

    override fun getGroupInfo(allowCache: Boolean): GroupInfo? {
        return apiCall(ApiService::groupInfo, if (allowCache) cacheManager.groupInfo() else null)
                ?.apply { cacheManager.groupInfo(this) }
    }

}
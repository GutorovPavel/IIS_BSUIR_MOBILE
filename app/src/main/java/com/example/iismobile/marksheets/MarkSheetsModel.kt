package com.example.iismobile.marksheets

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.data.MarkSheet

class MarkSheetsModel(
    api: ApiService,
    store: CredentialsStore,
    context: Context,
    cacheManager: ICacheManager,
        ) : ApiModel(api, store, context, cacheManager), IMarkSheetsModel {

    private val markSheetsCache: MutableList<MarkSheet> = mutableListOf()

    override fun getMarkSheets(allowCache: Boolean): List<MarkSheet>? =
        apiCall(ApiService::markSheet, if (allowCache && markSheetsCache.isNotEmpty()) markSheetsCache else null)
                ?.also {
                    if (!allowCache || markSheetsCache.isEmpty()) {
                        markSheetsCache.clear()
                        markSheetsCache.addAll(it)
                    }
                }


}
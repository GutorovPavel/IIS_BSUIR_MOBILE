package com.example.iismobile.markbook

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.data.MarkBook

class MarkBookModel (
    api: ApiService,
    store: CredentialsStore,
    applicationContext: Context,
    cacheManager: ICacheManager,
): ApiModel(api, store, applicationContext, cacheManager), IMarkBookModel {

    private var markBookCache: MarkBook? = null

    override fun getMarkBook(allowCache: Boolean): MarkBook? {
        return apiCall(ApiService::markBook, if (allowCache) markBookCache else null)
                ?.apply { markBookCache = this }
    }

}
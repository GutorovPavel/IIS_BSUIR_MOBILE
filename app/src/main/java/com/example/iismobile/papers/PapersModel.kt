package com.example.iismobile.papers

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.data.Paper
import com.example.iismobile.common.data.PaperPlaceCategory

class PapersModel (
    api: ApiService,
    store: CredentialsStore,
    applicationContext: Context,
    cacheManager: ICacheManager,
) : ApiModel(api, store, applicationContext, cacheManager), IPapersModel {

    private val papersCache: MutableList<Paper> = mutableListOf()
    private val placesCache: MutableList<PaperPlaceCategory> = mutableListOf()

    override fun getPapers(allowCache: Boolean): List<Paper>? =
        apiCall(ApiService::certificate, if (allowCache && papersCache.isNotEmpty()) papersCache else null)
            ?.also {
                if (!allowCache || papersCache.isEmpty()) {
                    papersCache.clear()
                    papersCache.addAll(it)
                }
            }

    override fun getPlaces(allowCache: Boolean): List<PaperPlaceCategory>? =
            apiCall(ApiService::places, if (allowCache && placesCache.isNotEmpty()) placesCache else null)
                    ?.also {
                        if (!allowCache || placesCache.isEmpty()) {
                            placesCache.clear()
                            placesCache.addAll(it)
                        }
                    }

}
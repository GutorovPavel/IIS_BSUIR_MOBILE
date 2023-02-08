package com.example.iismobile.preferences

import android.content.Context
import com.example.iismobile.common.ApiModel
import com.example.iismobile.common.ApiService
import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.cache.ICacheManager

class PreferencesModel (
    api: ApiService,
    store: CredentialsStore,
    appContext: Context,
    cacheManager: ICacheManager,
) : ApiModel(api, store, appContext, cacheManager), IPreferencesModel {

    override fun savePreference(field: String, key: String, value: Boolean): Boolean? {
        return apiCall({ api.savePreference(it, field, mapOf(key to value)) }, null)
            ?.let { value }
    }

}
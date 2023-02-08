package com.example.iismobile.common

import android.app.Application
import android.os.Build
import com.example.iismobile.common.cache.CacheManager
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.common.controller.CacheController
import com.example.iismobile.common.encryption.base.IEncryptionLayer
import com.example.iismobile.common.encryption.KeyStoreEncryptionLayer
import com.example.iismobile.common.encryption.LegacyEncryptionLayer
import com.example.iismobile.login.LoginModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    private val apiBaseUrl: String = "https://journal.bsuir.by/api/v1/"

    override val kodein = Kodein {
        import(androidXModule(this@App))

        /* Layer 0 */
        bind<ApiService>() with singleton { createApiService() }
        bind<CredentialsStore>() with singleton { CredentialsStore() }
        bind<ICacheManager>() with singleton { CacheManager() }

        /* Layer 1 */
        bind<LoginModel>() with singleton { LoginModel(applicationContext, instance(), createEncryptionStack(), instance()) }
        bind<SharedModel>() with singleton { SharedModel(instance(), instance(), this@App, instance()) }

        /* Layout 2 */
        bind<CacheController>() with singleton { CacheController(instance(), instance()) }
    }

    private fun createHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    private fun createApiService(): ApiService =
        ServiceFactory.createService(apiBaseUrl, createHttpClient())

    private fun createEncryptionStack(): List<IEncryptionLayer> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listOf(KeyStoreEncryptionLayer())
        } else {
            listOf(LegacyEncryptionLayer(this))
        }
    }

}
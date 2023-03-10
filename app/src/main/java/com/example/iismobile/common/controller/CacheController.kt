package com.example.iismobile.common.controller

import com.example.iismobile.common.CredentialsStore
import com.example.iismobile.common.data.NoCredentialsException
import com.example.iismobile.login.LoginModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CacheController (
    val loginModel: LoginModel,
    val store: CredentialsStore
) {

    fun <T> preloadCacheAndCall(data: Single<T>, checkMemory: Boolean = true): Single<T> {
        if (checkMemory && hasCredentials())
            return data

        return getCachedCredentials()
            .flatMap { isLoaded ->
                if (isLoaded) data
                else Single.create { i -> i.onError(NoCredentialsException()) }
            }
    }

    fun preloadCacheAndCall(task: Completable, checkMemory: Boolean = true): Completable {
        return preloadCacheAndCall(task.toSingleDefault(Unit), checkMemory)
                .ignoreElement()
    }

    /**
     * Read file from disk
     * Get token from file
     * Check if token is valid, save token to provider
     * If token is expired, read user/pass from file
     * If present, get token and save to provider. return true
     * If not present, return false
     * If failed to login with credentials, return false
     */
    fun getCachedCredentials(): Single<Boolean> =
            Single.create<Boolean> {
                val cache = loginModel.readCacheFile()
                        ?: return@create it.onSuccess(false)

                if (loginModel.checkUserToken(cache.token)) {
                    store.updateToken(cache.token)
                    return@create it.onSuccess(true)
                }

                val (login, password) = cache.credentials
                        ?: return@create it.onSuccess(false)

                val result = loginModel.login(login, password, saveToken=false, saveCredentials=true)
                if (result?.cookie == null)
                    return@create it.onSuccess(false)

                store.updateToken(result.cookie)
                it.onSuccess(true)
            }.subscribeOn(Schedulers.io())

    fun hasCredentials(): Boolean =
            store.hasToken()
}
package com.example.iismobile.preferences

import com.example.iismobile.common.data.InternalException
import com.example.iismobile.preferences.data.PreferenceType
import com.example.iismobile.preferences.data.ProfilePreferences
import com.example.iismobile.profile.IProfileModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PreferencesController(
    private val profileModel: IProfileModel,
    private val model: IPreferencesModel
) {

    fun getProfilePreferences(): Single<ProfilePreferences> =
        Single.create<ProfilePreferences> {
            val personalCv = profileModel.getPersonalCV(false)
                ?: return@create it.onError(InternalException("Не удалось получить данные профиля"))

            it.onSuccess(
                ProfilePreferences(
                personalCv.showRating,
                personalCv.published,
                personalCv.searchJob,
                personalCv.officeEmail,
                personalCv.officePassword
            )
            )
        }.subscribeOn(Schedulers.io())

    fun togglePreference(type: PreferenceType, value: Boolean): Completable =
        Completable.create {
            model.savePreference(type.field, type.key, value)
                ?: return@create it.onError(InternalException("Неудалось обновить настройки"))
            it.onComplete()
        }.subscribeOn(Schedulers.io())

}
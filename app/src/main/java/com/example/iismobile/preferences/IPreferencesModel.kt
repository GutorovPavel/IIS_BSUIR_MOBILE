package com.example.iismobile.preferences

interface IPreferencesModel {

    fun savePreference(field: String, key: String, value: Boolean): Boolean?

}
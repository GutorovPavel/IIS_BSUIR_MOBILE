package com.example.iismobile.preferences.data

enum class PreferenceType(val field: String, val key: String) {

    RATING("saveShowRating", "showRating"),
    PUBLISHED("savePublished", "published"),
    SEARCH_JOB("saveSearchJob", "searchJob")

}
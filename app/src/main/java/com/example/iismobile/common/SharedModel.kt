package com.example.iismobile.common

import android.content.Context
import com.example.iismobile.announcements.AnnouncementsModel
import com.example.iismobile.announcements.IAnnouncementsModel
import com.example.iismobile.common.cache.ICacheManager
import com.example.iismobile.group.GroupModel
import com.example.iismobile.group.IGroupModel
import com.example.iismobile.markbook.IMarkBookModel
import com.example.iismobile.markbook.MarkBookModel
import com.example.iismobile.marksheets.IMarkSheetsModel
import com.example.iismobile.marksheets.MarkSheetsModel
import com.example.iismobile.papers.IPapersModel
import com.example.iismobile.papers.PapersModel
import com.example.iismobile.preferences.IPreferencesModel
import com.example.iismobile.preferences.PreferencesModel
import com.example.iismobile.profile.IProfileModel
import com.example.iismobile.profile.ProfileModel

class SharedModel (
    apiService: ApiService, store: CredentialsStore, context: Context, cacheManager: ICacheManager
) : IProfileModel by ProfileModel(apiService, store, context, cacheManager),
    IPapersModel by PapersModel(apiService, store, context, cacheManager),
    IGroupModel by GroupModel(apiService, store, context, cacheManager),
    IMarkBookModel by MarkBookModel(apiService, store, context, cacheManager),
    IMarkSheetsModel by MarkSheetsModel(apiService, store, context, cacheManager),
    IAnnouncementsModel by AnnouncementsModel(apiService, store, context, cacheManager),
    IPreferencesModel by PreferencesModel(apiService, store, context, cacheManager)
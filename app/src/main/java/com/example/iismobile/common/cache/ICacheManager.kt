package com.example.iismobile.common.cache

import com.example.iismobile.common.data.DaySchedule
import com.example.iismobile.common.data.GroupInfo
import com.example.iismobile.common.data.PersonalCV
import com.example.iismobile.common.data.PersonalInformation

interface ICacheManager {

    fun dayScheduleCache(key: String, daySchedule: DaySchedule)

    fun dayScheduleCache(key: String): DaySchedule?

    fun personalCv(): PersonalCV?

    fun personalCv(personalCV: PersonalCV)

    fun personalInformation(): PersonalInformation?

    fun personalInformation(personalInformation: PersonalInformation)

    fun groupInfo(): GroupInfo?

    fun groupInfo(groupInfo: GroupInfo)

    fun markDirty()

}
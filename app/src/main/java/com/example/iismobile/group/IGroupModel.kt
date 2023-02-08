package com.example.iismobile.group

import com.example.iismobile.common.data.GroupInfo

interface IGroupModel {
    fun getGroupInfo(allowCache: Boolean): GroupInfo?
}
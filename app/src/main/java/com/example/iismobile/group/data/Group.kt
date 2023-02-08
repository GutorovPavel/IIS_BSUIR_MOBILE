package com.example.iismobile.group.data

import com.example.iismobile.common.data.GroupMember

data class Group (
    val number: String,
    val supervisor: GroupMember?,
    val leader: GroupMember?,
    val students: List<GroupMember>
)
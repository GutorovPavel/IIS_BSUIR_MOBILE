package com.example.iismobile.profile.data

import com.example.iismobile.common.data.PersonalCV
import com.example.iismobile.common.data.PersonalInformation
import com.example.iismobile.common.data.Reference
import com.example.iismobile.common.data.Skill

data class ProfileInfo(
    val fullName: String,
    val shortName: String,
    val birthDate: String,
    val facultyString: String,
    val rate: Int,
    val photoUrl: String,
    val skills: List<Skill>,
    val summary: String?,
    val references: List<Reference>,
    val personalInformation: PersonalInformation,
    val personalCV: PersonalCV
)
package com.example.iismobile.common.data

import com.google.gson.annotations.SerializedName

data class MarkSheet(
    val id: Int,
    val subject: Subject,
    @SerializedName("markSheetType")
        val type: MarkSheetType,
    val employee: Employee,
    val absentDate: String,
    val createDate: String,
    val reason: Int, // TODO: what is that?
    @SerializedName("status")
        val statusString: String,
    val student: Student,
    val rejectionReason: String?,
    val totalApproved: Int,
    val retakeCount: Int,
    val term: Int,
    val price: Float?,
    val hours: Int?,
) {
    enum class Status(val string: String) {
        PRINTED("напечатана"), NOP("NOP")
    }

    fun status(): Status =
            Status.values().firstOrNull { it.string == statusString } ?: Status.NOP

    fun price(): Float {
        if (price != null)
            return price

        return type.coefficient * employee.rank.price
    }
}
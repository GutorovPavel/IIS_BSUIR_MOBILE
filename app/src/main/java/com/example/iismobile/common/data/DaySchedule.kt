package com.example.iismobile.common.data

data class DaySchedule (
    val studentGroup: Group,
    val todayDate: String,
    val todaySchedules: List<ScheduleEntry>,
    val currentWeek: Int,
    val dateStart: String,
    val dateEnd: String
) {

    fun uniqueCount(): Int =
            todaySchedules.distinctBy { it.startTime }.size

}
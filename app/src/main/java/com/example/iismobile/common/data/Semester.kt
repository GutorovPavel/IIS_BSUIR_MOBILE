package com.example.iismobile.common.data

class Semester(
        val index: Int,
        averageMark: Float,
        marks: List<Mark>
) : SemesterData(averageMark, marks)
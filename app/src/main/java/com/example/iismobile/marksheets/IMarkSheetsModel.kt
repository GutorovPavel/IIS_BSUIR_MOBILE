package com.example.iismobile.marksheets

import com.example.iismobile.common.data.MarkSheet

interface IMarkSheetsModel {

    fun getMarkSheets(allowCache: Boolean): List<MarkSheet>?

}
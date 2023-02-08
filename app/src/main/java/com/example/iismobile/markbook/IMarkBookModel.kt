package com.example.iismobile.markbook

import com.example.iismobile.common.data.MarkBook

interface IMarkBookModel {

    fun getMarkBook(allowCache: Boolean): MarkBook?

}
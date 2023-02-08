package com.example.iismobile.papers

import com.example.iismobile.common.data.Paper
import com.example.iismobile.common.data.PaperPlaceCategory

interface IPapersModel {

    fun getPapers(allowCache: Boolean): List<Paper>?

    fun getPlaces(allowCache: Boolean): List<PaperPlaceCategory>?

}
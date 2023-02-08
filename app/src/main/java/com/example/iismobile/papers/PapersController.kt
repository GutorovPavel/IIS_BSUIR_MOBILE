package com.example.iismobile.papers

import com.example.iismobile.common.data.InternalException
import com.example.iismobile.common.data.Paper
import com.example.iismobile.common.data.PaperPlaceCategory
import com.example.iismobile.papers.IPapersModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PapersController (val model: IPapersModel) {

    fun updatePapers(forceUpdate: Boolean): Single<List<Paper>> =
        Single.create<List<Paper>> {
            val papers = model.getPapers(allowCache = !forceUpdate)
                ?: return@create it.onError(InternalException("Невозможно получить данные о справках"))

            it.onSuccess(papers)
        }.subscribeOn(Schedulers.io())

    fun updatePlaces(forceUpdate: Boolean): Single<List<PaperPlaceCategory>> =
            Single.create<List<PaperPlaceCategory>> {
                val places = model.getPlaces(allowCache = !forceUpdate)
                        ?: return@create it.onError(InternalException("Невозможно получить данные о справках"))
                Thread.sleep(10000)
                it.onSuccess(places)
            }.subscribeOn(Schedulers.io())

}
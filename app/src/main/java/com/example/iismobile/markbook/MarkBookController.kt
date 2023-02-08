package com.example.iismobile.markbook

import com.example.iismobile.common.data.InternalException
import com.example.iismobile.common.data.MarkBook
import com.example.iismobile.markbook.IMarkBookModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MarkBookController (
        val model: IMarkBookModel
) {

    fun updateMarkBook(forceUpdate: Boolean): Single<MarkBook> =
            Single.create<MarkBook> {
                val markBook = model.getMarkBook(allowCache = !forceUpdate)
                        ?: return@create it.onError(InternalException("Невозможно получить данные о справках"))

                it.onSuccess(markBook)
            }.subscribeOn(Schedulers.io())
}
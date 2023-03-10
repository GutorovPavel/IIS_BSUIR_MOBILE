package com.example.iismobile.markbook

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.iismobile.R
import com.example.iismobile.common.api.views.ModelDataFragment
import com.example.iismobile.common.data.MarkBook
import com.example.iismobile.common.data.Semester
import com.example.iismobile.common.utils.asVisibility
import com.example.iismobile.markbook.semesters.SemestersAdapter
import com.example.iismobile.markbook.semesters.SemestersScrollDelegate
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.f_markbook.view.*
import org.kodein.di.generic.instance
import kotlin.math.max

class MarkBookFragment : ModelDataFragment<MarkBookFragment.MarkBookViewState, MarkBook>(R.layout.f_markbook) {

    sealed class MarkBookViewState {
        abstract val progressVisibility: Boolean
        abstract val markBookVisibility: Boolean
        abstract val errorViewVisibility: Boolean

        object MarkBookLoading: MarkBookViewState() {
            override val progressVisibility: Boolean = true
            override val markBookVisibility: Boolean = false
            override val errorViewVisibility: Boolean = false
        }
        class MarkBookFilled(val markBook: MarkBook): MarkBookViewState() {
            override val progressVisibility: Boolean = false
            override val markBookVisibility: Boolean = true
            override val errorViewVisibility: Boolean = false
        }
        class MarkBookFailed(val message: String, val retryHandler: View.() -> Unit): MarkBookViewState() {
            override val progressVisibility: Boolean = false
            override val markBookVisibility: Boolean = false
            override val errorViewVisibility: Boolean = true
        }
    }

    private val model: IMarkBookModel by instance()

    private val controller by lazy { MarkBookController(model) }

    private val semestersAdapter by lazy { SemestersAdapter() }

    override fun View.onView() {
        val snapHelper = PagerSnapHelper()
        with(mark_book_view) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = semestersAdapter
            snapHelper.attachToRecyclerView(this)
            addOnScrollListener(SemestersScrollDelegate(::onItemIndexChanged, ::onAlphaChanged))
        }

        updateData(true)
    }

    override fun getLoadingState(): MarkBookViewState =
        MarkBookViewState.MarkBookLoading

    override fun getFilledState(it: MarkBook): MarkBookViewState =
        MarkBookViewState.MarkBookFilled(it)

    override fun getErrorState(it: Throwable, msg: String, retryAction: View.() -> Unit): MarkBookViewState =
        MarkBookViewState.MarkBookFailed(msg, retryAction)

    override fun getDataUpdate(forceUpdate: Boolean): Single<MarkBook> =
            controller.updateMarkBook(forceUpdate)

    private fun onItemIndexChanged(index: Int) {
        semestersAdapter.data.getOrNull(index)?.let {
            view?.updateSemesterHeader(it)
        }
    }

    private fun onAlphaChanged(alpha: Float) = view?.run {
        markbook_title.alpha = alpha
        markbook_average_mark.alpha = alpha
    } ?: Unit

    private fun View.updateSemesterHeader(item: Semester) {
        markbook_title.text = "??????????????: ${item.index}"
        markbook_average_mark.text = String.format("%.2f", item.averageMark)
    }

    override fun View.applyState(newState: MarkBookViewState) = with(newState) {
        mark_book_view.visibility = markBookVisibility.asVisibility()
        markbook_average_mark_layout.visibility = markBookVisibility.asVisibility()

        if (this is MarkBookViewState.MarkBookLoading)
            markbook_title.text = "?????????????????? ???????????? ???? ??????????????..."

        if (this is MarkBookViewState.MarkBookFilled) {
            semestersAdapter.setSemesters(markBook.semestersList)
            val index = mark_book_view.computeHorizontalScrollOffset() /
                    max(1, mark_book_view.computeHorizontalScrollExtent())
            semestersAdapter.data.getOrNull(index)?.let {
                updateSemesterHeader(it)
            }
        }
    }

}
package com.example.iismobile.profile.dialogs

import android.content.Context
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.example.iismobile.R
import com.example.iismobile.common.api.views.BaseDialog
import com.example.iismobile.common.controller.CacheController
import com.example.iismobile.profile.ProfileController
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class SummarySubmitDialog (
    private val cacheController: CacheController,
    private val controller: ProfileController,
    private val updateHandler: () -> Unit,
    private  val cancelHandler: (needAlert: Boolean) -> Unit
) : BaseDialog(title = R.string.summary_submit_title) {

    override fun onCancel() {
        super.onCancel()
        cancelHandler(false)
    }

    private fun onSubmit(text: String) {
        cacheController.preloadCacheAndCall(controller.updateSummary(text))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateHandler)
                { cancelHandler(true) }
    }

    override fun newDialog(context: Context, bundle: Bundle): MaterialDialog {
        val displayText = bundle.getString("displayText", "")
        val text = bundle.getString("text", "")

        return super.newDialog(context, bundle)
                .message(text=context.getString(R.string.summary_submit_message, displayText))
                    { html() }
                .cancelable(true)
                .positiveButton(R.string.summary_submit) {
                    onSubmit(text)
                }
                .negativeButton(R.string.cancel) {
                    it.dismiss()
                    onCancel()
                }
    }

}
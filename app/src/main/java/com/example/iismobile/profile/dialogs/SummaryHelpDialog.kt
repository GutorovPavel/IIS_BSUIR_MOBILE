package com.example.iismobile.profile.dialogs

import android.content.Context
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.example.iismobile.R
import com.example.iismobile.common.api.views.BaseDialog

class SummaryHelpDialog : BaseDialog(title = R.string.info_title){

    override fun newDialog(context: Context, bundle: Bundle): MaterialDialog {
        return super.newDialog(context, bundle)
                .message(R.string.info_help) { html() }
                .cancelable(true)
    }

}
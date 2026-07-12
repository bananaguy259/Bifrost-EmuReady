package com.solar.aurora.ui

import androidx.appcompat.app.AppCompatActivity
import com.solar.aurora.R

class RagnarokWarningDialog(
    private val alertDialog: AuroraAlertDialog = AuroraAlertDialog()
) {

    fun show(
        activity: AppCompatActivity,
        onConfirm: () -> Unit,
        onCancel: () -> Unit
    ) {
        val title = activity.getString(R.string.ragnarok_warning_title)
        val subtitle = activity.getString(R.string.ragnarok_warning_subtitle)
        val body = activity.getString(R.string.ragnarok_warning_body)

        alertDialog.show(
            activity = activity,
            title = title,
            subtitle = subtitle,
            body = body,
            positiveLabelResId = R.string.ragnarok_warning_confirm,
            negativeLabelResId = R.string.action_cancel,
            cancelable = false,
            onConfirm = onConfirm,
            onCancel = onCancel
        )
    }
}

package com.solar.aurora.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.solar.aurora.R

class AuroraAlertDialog {

    fun show(
        activity: AppCompatActivity,
        title: String,
        subtitle: String?,
        body: String?,
        positiveLabelResId: Int,
        negativeLabelResId: Int?,
        cancelable: Boolean,
        isDestructive: Boolean = false,
        onConfirm: () -> Unit,
        onCancel: () -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.dialog_aurora_alert, null)

        val titleView = view.findViewById<TextView>(R.id.dialogTitle)
        val subtitleView = view.findViewById<TextView>(R.id.dialogSubtitle)
        val bodyView = view.findViewById<TextView>(R.id.dialogBody)
        val cancelButton = view.findViewById<MaterialButton>(R.id.dialogCancelButton)
        val confirmButton = view.findViewById<MaterialButton>(R.id.dialogConfirmButton)

        titleView.text = title

        if (subtitle.isNullOrEmpty()) {
            subtitleView.visibility = View.GONE
        } else {
            subtitleView.visibility = View.VISIBLE
            subtitleView.text = subtitle
        }

        if (body.isNullOrEmpty()) {
            bodyView.visibility = View.GONE
        } else {
            bodyView.visibility = View.VISIBLE
            bodyView.text = body
        }

        confirmButton.text = activity.getString(positiveLabelResId)
        cancelButton.backgroundTintList = null
        confirmButton.setBackgroundResource(
            if (isDestructive) R.drawable.button_stop_pill else R.drawable.button_primary_pill
        )
        confirmButton.backgroundTintList = null

        if (negativeLabelResId != null) {
            cancelButton.visibility = View.VISIBLE
            cancelButton.text = activity.getString(negativeLabelResId)
        } else {
            cancelButton.visibility = View.GONE
        }

        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .setCancelable(cancelable)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(cancelable)

        confirmButton.setOnClickListener {
            dialog.dismiss()
            onConfirm()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
            onCancel()
        }

        dialog.show()
    }
}

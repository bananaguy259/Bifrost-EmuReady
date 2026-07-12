package com.solar.aurora.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.solar.aurora.MainActivity
import com.solar.aurora.R
import com.solar.aurora.services.LEDService

class AuroraWidgetProvider : AppWidgetProvider() {

    companion object {
        const val EXTRA_AUTO_START_FROM_WIDGET = "autoStartFromWidget"

        fun updateAllWidgets(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, AuroraWidgetProvider::class.java))
            if (ids.isNotEmpty()) {
                for (id in ids) {
                    pushUpdate(context, manager, id)
                }
            }
        }

        private fun pushUpdate(context: Context, manager: AppWidgetManager, widgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_aurora)
            val isRunning = LEDService.isRunning

            views.setTextViewText(R.id.widgetStatusText, if (isRunning) "STOP" else "START")
            views.setInt(
                R.id.widgetRoot,
                "setBackgroundResource",
                if (isRunning) R.drawable.button_stop_pill else R.drawable.button_primary_pill
            )

            val pendingIntent = if (isRunning) {
                val stopIntent = Intent(context, LEDService::class.java).apply {
                    action = LEDService.ACTION_STOP
                }
                PendingIntent.getService(
                    context,
                    widgetId,
                    stopIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                val launchIntent = Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_MAIN
                    addCategory(Intent.CATEGORY_LAUNCHER)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    putExtra(EXTRA_AUTO_START_FROM_WIDGET, true)
                }
                PendingIntent.getActivity(
                    context,
                    widgetId,
                    launchIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
            views.setOnClickPendingIntent(R.id.widgetRoot, pendingIntent)

            manager.updateAppWidget(widgetId, views)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (id in appWidgetIds) {
            pushUpdate(context, appWidgetManager, id)
        }
    }
}

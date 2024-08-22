package com.example.appiconchange

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class IconChangeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val currentIcon = prefs.getString("currentIcon", "Icon1")

        val packageManager: PackageManager = context.packageManager
        val mainActivityName = "${context.packageName}.MainActivity"

        packageManager.setComponentEnabledSetting(
            ComponentName(context, "$mainActivityName.$currentIcon"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}
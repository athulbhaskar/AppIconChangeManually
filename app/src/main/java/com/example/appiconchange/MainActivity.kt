package com.example.appiconchange

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeIconButton: Button = findViewById(R.id.changeIconButton)
        val changeIconButton2: Button = findViewById(R.id.changeIconButton2)
        changeIconButton.setOnClickListener {
            changeAppIcon("Icon2") // Change to Icon2 when button is clicked
        }
        changeIconButton2.setOnClickListener {
            changeAppIcon("Icon1")
        }
    }

    private fun changeAppIcon(iconType: String) {
        val packageManager: PackageManager = packageManager
        val mainActivityName = "${packageName}.MainActivity"

        // Disable all activity-alias components
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$mainActivityName.Icon1"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$mainActivityName.Icon2"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        // Enable the selected icon
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$mainActivityName.$iconType"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        // Save the current icon type to SharedPreferences
        val prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("currentIcon", iconType).apply()
        killAppAndRestart()
    }


    fun killAppAndRestart() {
        val packageManager = packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        startActivity(mainIntent)
        exitProcess(0)
    }

}
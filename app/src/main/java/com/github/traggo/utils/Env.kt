package com.github.traggo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.ContextCompat.getString
import com.github.traggo.R
import com.github.traggo.utils.Constants.TRAGGO_TAG

object Env {
    val TRAGGO_PORT_ENV = "TRAGGO_PORT"
    val TRAGGO_DEFAULT_USER_NAME_ENV = "TRAGGO_DEFAULT_USER_NAME"
    val TRAGGO_DEFAULT_USER_PASS_ENV = "TRAGGO_DEFAULT_USER_PASS"

    val TRAGGO_PASS_STRENGTH_ENV = "TRAGGO_PASS_STRENGTH"

    val TRAGGO_LOG_LEVEL_ENV = "TRAGGO_LOG_LEVEL"

    val TRAGGO_DATABASE_DIALECT_ENV = "TRAGGO_DATABASE_DIALECT_ENV"
    val TRAGGO_DATABASE_PATH_ENV = "TRAGGO_DATABASE_CONNECTION"

    public fun getDefaultEnv(): Map<String, String> {
        val hm = HashMap<String, String>()

        hm.put(TRAGGO_PORT_ENV, "3030")
        hm.put(TRAGGO_DEFAULT_USER_NAME_ENV, "admin")
        hm.put(TRAGGO_DEFAULT_USER_PASS_ENV, "admin")
        hm.put(TRAGGO_PASS_STRENGTH_ENV, "10")
        hm.put(TRAGGO_LOG_LEVEL_ENV, "info")
        hm.put(TRAGGO_DATABASE_DIALECT_ENV, "sqlite3")
        hm.put(TRAGGO_DATABASE_PATH_ENV, Paths.getTraggoDb().absolutePath)

        return hm
    }

    public fun getEnvFromPrefs(
        context: Context,
        prefs: SharedPreferences,
    ): Map<String, String> {
        val port: String = prefs.getString(context.getString(R.string.key_setting_traggo_server_port_number), "3030")!!
        val stre: String = prefs.getString(context.getString(R.string.key_setting_traggo_server_password_strength), "10")!!
        val log: String = prefs.getString(context.getString(R.string.key_setting_traggo_server_log_level), "info")!!

        val hm = HashMap<String, String>()
        hm.putAll(getDefaultEnv())

        hm.put(TRAGGO_PORT_ENV, port)
        hm.put(TRAGGO_PASS_STRENGTH_ENV, stre)
        hm.put(TRAGGO_LOG_LEVEL_ENV, log)

        Log.i(TRAGGO_TAG, "Using Traggo settings: $hm")

        return hm
    }
}

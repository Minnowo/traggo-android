package com.github.traggo

import android.app.Application
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.annotation.StringRes
import androidx.preference.PreferenceManager
import com.github.traggo.service.ToastService
import com.github.traggo.service.TraggoService
import com.github.traggo.utils.Constants
import com.github.traggo.utils.Constants.TRAGGO_TAG
import com.github.traggo.utils.Env
import com.github.traggo.utils.Paths

class App : Application() {
    companion object {
        private lateinit var instance: App

        fun i(): App = instance
    }

    private lateinit var ts: ToastService

    val traggoService = TraggoService()

    override fun onCreate() {
        super.onCreate()

        instance = this

        Paths.init(this)

        Log.i(TRAGGO_TAG, "Starting toast service")

        ts = ToastService(applicationContext)
        ts.start()

        Log.i(TRAGGO_TAG, "Starting Traggo Server")

        traggoService.startTraggo()
    }

    fun getTraggoEnv(): Map<String, String> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val traggoEnv: HashMap<String, String> = HashMap()
        traggoEnv.putAll(Env.getEnvFromPrefs(applicationContext, prefs))
        return traggoEnv
    }

    fun showToast(
        @StringRes resourceStringId: Int,
    ) {
        showToast(getString(resourceStringId))
    }

    fun showToast(
        @StringRes resourceStringId: Int,
        vararg fmtArgs: Any?,
    ) {
        showToast(getString(resourceStringId, *fmtArgs))
    }

    fun showToast(message: String) {
        if (ts.handler == null) {
            return
        }
        val msg = Message.obtain()
        val data = Bundle()

        data.putString(Constants.TOAST_TEXT_KEY, message)

        msg.data = data

        ts.handler!!.sendMessage(msg)
    }
}

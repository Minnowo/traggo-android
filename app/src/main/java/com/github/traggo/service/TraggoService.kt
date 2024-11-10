package com.github.traggo.service

import com.github.traggo.App
import com.github.traggo.R
import java.util.concurrent.atomic.AtomicBoolean

class TraggoService {
    private val isTraggoRunning = AtomicBoolean(false)
    private val traggoRunnable: TraggoRunnable = TraggoRunnable(isTraggoRunning, HashMap())

    fun isRunning(): Boolean = isTraggoRunning.get()

    fun startTraggo() {
        if (isRunning()) {
            return
        }

        Thread {
            App.i().showToast(R.string.toast_traggo_running)

            traggoRunnable.updateEnv(App.i().getTraggoEnv())
            traggoRunnable.run()

            val exit = traggoRunnable.getExitStatus()

            if (exit != null) {
                App.i().showToast(R.string.toast_traggo_exited, exit)
            }
        }.start()
    }
}

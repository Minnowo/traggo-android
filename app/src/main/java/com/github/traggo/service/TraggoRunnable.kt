package com.github.traggo.service

import android.util.Log
import com.github.traggo.utils.Constants.TRAGGO_TAG
import com.github.traggo.utils.Env
import com.github.traggo.utils.Paths
import com.github.traggo.utils.ProcessUtils
import kotlinx.coroutines.Runnable
import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

class TraggoRunnable(
    private val running: AtomicBoolean,
) : Runnable {
    var process: Process? = null

    fun getExitStatus(): Int? {
        if (process == null || ProcessUtils.isProcessAlive(process!!)) {
            return null
        }
        return process!!.exitValue()
    }

    private fun makeFileExecutable(file: File) {
        try {
            Log.d(TRAGGO_TAG, "chmod 500 $file")

            val p = ProcessBuilder("chmod", "500", file.path).start()
            p.waitFor()
        } catch (e: IOException) {
            Log.e(TRAGGO_TAG, "Failed to chmod $file", e)
        } catch (e: InterruptedException) {
            Log.e(TRAGGO_TAG, "Failed to chmod $file", e)
        }
    }

    override fun run() {
        val traggoPath = Paths.getTraggoPath()

        Log.d(TRAGGO_TAG, "Traggo is located at $traggoPath")

        if (!traggoPath.exists()) {
            Log.i(TRAGGO_TAG, "Traggo does not exist!!!")

            return
        }

        makeFileExecutable(traggoPath)

        val pb = ProcessBuilder(traggoPath.path)
        pb.environment().putAll(Env.getDefaultEnv())

        val p = pb.start()

        process = p

        Log.i(TRAGGO_TAG, "Traggo is running!")

        val isRunning = ProcessUtils.isProcessAlive(p)

        running.set(isRunning)

        if (isRunning) {
            ProcessUtils.logProcess(p)
        }

        p.waitFor()

        running.set(false)

        Log.i(TRAGGO_TAG, "Traggo has stopped! Exited with status: ${getExitStatus()}")
    }
}

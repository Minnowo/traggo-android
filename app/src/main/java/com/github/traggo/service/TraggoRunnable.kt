package com.github.traggo.service

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.github.traggo.utils.Constants.TRAGGO_TAG
import com.github.traggo.utils.Env
import com.github.traggo.utils.Paths
import kotlinx.coroutines.Runnable
import java.io.File
import java.io.IOException

class TraggoRunnable(
    private val context: Context,
) : Runnable {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        val traggoPath = Paths.getTraggoPath(context)

        Log.d(TRAGGO_TAG, "Traggo is located at $traggoPath")

        if (!traggoPath.exists()) {
            Log.i(TRAGGO_TAG, "Traggo does not exist!!!")

            return
        }

        makeFileExecutable(traggoPath)

        while (true) {
            val pb = ProcessBuilder(traggoPath.path)

            pb.environment().putAll(Env.getDefaultEnv(context))

            val p =
                pb
                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                    .redirectError(ProcessBuilder.Redirect.PIPE)
                    .start()

            Log.i(TRAGGO_TAG, "Traggo is running!")

            p.waitFor()

            Log.i(TRAGGO_TAG, "Traggo has stopped!")

            val outputText = p.inputStream.bufferedReader().readText()

            Log.i(TRAGGO_TAG, "Traggo stdout: $outputText")
            Log.i(TRAGGO_TAG, "Traggo exited with return code: ${p.exitValue()}")
        }
    }
}

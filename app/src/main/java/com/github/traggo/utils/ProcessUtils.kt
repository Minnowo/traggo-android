package com.github.traggo.utils

import android.os.Build
import android.util.Log
import com.github.traggo.CompileTime
import com.github.traggo.utils.Constants.TRAGGO_TAG
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object ProcessUtils {
    fun isProcessAlive(process: Process): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return process.isAlive
        }
        return try {
            process.exitValue()
            false
        } catch (e: IllegalThreadStateException) {
            true
        }
    }

    fun logProcess(process: Process) {
        // Do this always???
        if (!CompileTime.IS_DEBUG) {
            return
        }

        Thread {
            Log.i(TRAGGO_TAG, "Starting to log error stream of process")

            val reader = BufferedReader(InputStreamReader(process.errorStream))

            while (true) {
                val line: String?
                try {
                    line = reader.readLine() ?: break
                } catch (e: IOException) {
                    break
                }

                Log.e(TRAGGO_TAG, line)
            }

            reader.close()

            Log.i(TRAGGO_TAG, "Process logger closed")
        }.start()

        Thread {
            Log.i(TRAGGO_TAG, "Starting to log input stream of process")

            val reader = BufferedReader(InputStreamReader(process.inputStream))

            while (true) {
                val line: String?
                try {
                    line = reader.readLine() ?: break
                } catch (e: IOException) {
                    break
                }

                Log.i(TRAGGO_TAG, line)
            }

            reader.close()

            Log.i(TRAGGO_TAG, "Process logger closed")
        }.start()
    }
}

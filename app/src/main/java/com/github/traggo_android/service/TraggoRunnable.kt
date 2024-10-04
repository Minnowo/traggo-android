package com.github.traggo_android.service

import android.util.Log
import kotlinx.coroutines.Runnable
import java.io.File

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.traggo_android.utils.Env
import com.github.traggo_android.utils.Paths
import java.io.IOException

class TraggoRunnable(private val context:Context) : Runnable{

    private fun makeFileExecutable(file: File){

        try {
            Log.d(TAG, "chmod 500 $file")

            val p = ProcessBuilder("chmod", "500", file.path).start()
            p.waitFor()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to chmod $file", e)
        } catch (e: InterruptedException) {
            Log.e(TAG, "Failed to chmod $file", e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {

        val traggoPath = Paths.getTraggoPath(context);

        Log.d(TAG, "Traggo is located at $traggoPath")

        if(!traggoPath.exists()){

            Log.i(TAG,"Traggo does not exist!!!")

            return;
        }

        makeFileExecutable(traggoPath)

        while (true) {

            val pb = ProcessBuilder(traggoPath.path)

            pb.environment().putAll(Env.getDefaultEnv(context))

            val p = pb
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start();

            Log.i(TAG, "Traggo is running!")

            p.waitFor()

            Log.i(TAG, "Traggo has stopped!")

            val outputText = p.inputStream.bufferedReader().readText()

            Log.i(TAG, "Traggo stdout: $outputText")
            Log.i(TAG, "Traggo exited with return code: ${p.exitValue()}")
        }
    }
}
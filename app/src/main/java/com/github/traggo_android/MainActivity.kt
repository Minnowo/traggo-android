package com.github.traggo_android

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    fun makeFileExecutable(file: File){

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thread {

            val nativeDir = applicationContext.applicationInfo.nativeLibraryDir

            val traggoPath = File("$nativeDir/libtraggo.so")
            val targgoDbPath = applicationContext.getDatabasePath("traggo.db").absolutePath

            Log.d(TAG, "Traggo is located at $traggoPath")

            if(!traggoPath.exists()){

                Log.i(TAG,"Traggo does not exist!!!")

                return@thread;
            }

            makeFileExecutable(traggoPath)

            val pb =  ProcessBuilder(traggoPath.path)

            pb.environment()["TRAGGO_DATABASE_CONNECTION"] = targgoDbPath

            val p =pb
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start();

            Log.i(TAG,"Traggo is running!")

            p.waitFor()

            Log.i(TAG,"Traggo has stopped!")

            val outputText = p.inputStream.bufferedReader().readText()

            Log.i(TAG, "Traggo stdout: $outputText")
            Log.i(TAG, "Traggo exited with return code: ${p.exitValue()}")
        }
    }



}

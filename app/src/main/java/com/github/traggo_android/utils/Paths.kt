package com.github.traggo_android.utils

import android.content.Context
import java.io.File

object Paths {

private  val TRAGGO_PATH = "libtraggo.so";
    private  val TRAGGO_DB = "traggo.db";

    fun getTraggoPath(context: Context) : File{

        return File(context.applicationContext.applicationInfo.nativeLibraryDir, TRAGGO_PATH)
    }

    fun getTraggoDb(context: Context) : File{
        return context.getDatabasePath(TRAGGO_DB)
    }
}
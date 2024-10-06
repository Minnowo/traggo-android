package com.github.traggo.utils

import android.content.Context
import java.io.File

object Paths {
    private val TRAGGO_PATH = "libtraggo.so"
    private val TRAGGO_DB = "traggo.db"

    fun getTraggoPath(context: Context): File = File(context.applicationContext.applicationInfo.nativeLibraryDir, TRAGGO_PATH)

    fun getTraggoDb(context: Context): File = context.getDatabasePath(TRAGGO_DB)
}

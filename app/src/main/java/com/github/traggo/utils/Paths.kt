package com.github.traggo.utils

import android.content.Context
import java.io.File

object Paths {
    private val TRAGGO_PATH = "libtraggo.so"
    private val TRAGGO_DB = "traggo.db"

    private var traggoPath: File? = null
    private var traggoDBPath: File? = null

    fun init(context: Context) {
        if (traggoPath != null) {
            return
        }
        traggoPath = File(context.applicationContext.applicationInfo.nativeLibraryDir, TRAGGO_PATH)
        traggoDBPath = context.getDatabasePath(TRAGGO_DB)
    }

    fun getTraggoPath(): File = traggoPath!!

    fun getTraggoDb(): File = traggoDBPath!!
}

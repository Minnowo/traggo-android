package com.github.traggo_android.utils

import android.content.Context

object Env {

    val TRAGGO_PORT_ENV = "TRAGGO_PORT";
    val TRAGGO_DEFAULT_USER_NAME_ENV = "TRAGGO_DEFAULT_USER_NAME";
    val TRAGGO_DEFAULT_USER_PASS_ENV = "TRAGGO_DEFAULT_USER_PASS";

    val TRAGGO_PASS_STRENGTH_ENV = "TRAGGO_PASS_STRENGTH";

    val TRAGGO_LOG_LEVEL_ENV = "TRAGGO_LOG_LEVEL";

    val TRAGGO_DATABASE_DIALECT_ENV = "TRAGGO_DATABASE_DIALECT_ENV";
    val TRAGGO_DATABASE_PATH_ENV = "TRAGGO_DATABASE_CONNECTION";

    public fun getDefaultEnv(context: Context): Map<String, String> {

        val hm = HashMap<String, String>()

        hm.put(TRAGGO_PORT_ENV, "3030")
        hm.put(TRAGGO_DEFAULT_USER_NAME_ENV, "admin")
        hm.put(TRAGGO_DEFAULT_USER_PASS_ENV, "admin")
        hm.put(TRAGGO_PASS_STRENGTH_ENV, "10")
        hm.put(TRAGGO_LOG_LEVEL_ENV, "info")
        hm.put(TRAGGO_DATABASE_DIALECT_ENV, "sqlite3")
        hm.put(TRAGGO_DATABASE_PATH_ENV, Paths.getTraggoDb(context).absolutePath)

        return hm;
    }
}
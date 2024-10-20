package com.github.traggo.service

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.github.traggo.utils.Constants

class ToastService(
    val context: Context,
) : Thread() {
    var handler: Handler? = null

    override fun run() {
        Looper.prepare()

        handler =
            object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    if (msg.data != null) {
                        val m = msg.data!!.getString(Constants.TOAST_TEXT_KEY)

                        Toast.makeText(context, m, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        Looper.loop()
    }
}

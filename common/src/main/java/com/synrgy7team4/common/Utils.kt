package com.synrgy7team4.common

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun makeToast(context: Context, message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(context, "An unexpected error occurred", Toast.LENGTH_LONG).show()
    }
}

fun makeSnackbar(view: View, message: CharSequence) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

fun formatRupiah(number: String): String {
    val numberFormat = String.format("%,d", number.toDouble().toInt())
    return numberFormat
}

object Log {
    fun d(tag: String, msg: String): Int {
        println("DEBUG: $tag: $msg")
        return 0
    }

    fun i(tag: String, msg: String): Int {
        println("INFO: $tag: $msg")
        return 0
    }

    fun w(tag: String, msg: String): Int {
        println("WARN: $tag: $msg")
        return 0
    }

    fun e(tag: String, msg: String): Int {
        println("ERROR: $tag: $msg")
        return 0
    }
}
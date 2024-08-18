package com.synrgy7team4.common

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun makeToast(context: Context, message: String?) =
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

fun makeSnackbar(view: View, message: CharSequence) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
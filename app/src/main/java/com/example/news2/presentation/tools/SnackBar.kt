package com.example.news2.presentation.tools

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun SnackBar(view: View, text: String) {
    val snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    snack.show()
}
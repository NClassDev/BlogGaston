package com.example.firebasecursogaston.core

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    val imn = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imn.hideSoftInputFromWindow(windowToken, 0)
}


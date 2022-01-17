package com.umang.weatherapp.utils

import android.app.Service
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager

import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat


/**
 * to convert utc to date string
 *
 * @return : date string
 */
fun Long.toDate() :String{
    val formatter =  SimpleDateFormat(DATE_FORMAT,Locale.US)
    return formatter.format(Date(this*1000))
}

/**
 * to convert utc to time string
 *
 * @return : time string
 */
fun Long.toTime() :String{
    val formatter =  SimpleDateFormat(TIME_FORMAT,Locale.US)
    return formatter.format(Date(this*1000)).uppercase()
}

/**
 * To hide Keyboard
 *
 */
fun EditText.hideSoftKeyboard() {
    this.requestFocus()
    val imm: InputMethodManager =
        this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

/**
 * Show keyboard :To open keyboard
 *
 */
fun EditText.showKeyboard() {
    this.requestFocus()
    val imm: InputMethodManager =
        this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

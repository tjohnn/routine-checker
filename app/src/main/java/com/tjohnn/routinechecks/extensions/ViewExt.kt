package com.tjohnn.routinechecks.extensions

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes

fun View.hide() {
    if(this.visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

fun View.show() {
    if(this.visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.isVisible()  = this.visibility == View.VISIBLE

/**
 * Helps to differentiate whether edit text value is changed programatically or by user
 * in order to avoid triggering onTextChange when not needed
 * An example use case is the cart quantity editor and its surrounding buttons
 */
fun EditText.updateText(text: String) {
    val focused = hasFocus()
    if (focused) {
        clearFocus()
    }
    setText(text)
    if (focused) {
        requestFocus()
    }
}

fun TextView.setDrawableStart(@DrawableRes drawable: Int) {
    setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
}
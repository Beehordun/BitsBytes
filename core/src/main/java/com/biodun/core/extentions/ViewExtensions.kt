package com.biodun.core.extentions

import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import android.view.View.INVISIBLE

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.gone() {
    this.visibility = GONE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

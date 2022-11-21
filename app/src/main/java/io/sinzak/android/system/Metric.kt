package io.sinzak.android.system

import android.content.res.Resources


val Int.dp get() =
    Resources.getSystem().displayMetrics.density * this.toFloat()

val Float.dp get() =
    Resources.getSystem().displayMetrics.density * this.toFloat()
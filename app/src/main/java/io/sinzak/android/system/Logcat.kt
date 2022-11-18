package io.sinzak.android.system

import android.util.Log
import io.sinzak.android.constants.DEBUG_MODE

fun LogDebug(tag: String, text: String) {
    if (DEBUG_MODE)
        Log.d(tag, text)
}

fun LogError(tag: String, text: String) {
    if (DEBUG_MODE)
        Log.e(tag, text)
}

fun LogError(e: Throwable) {
    if (DEBUG_MODE)
        e.printStackTrace()
}

fun LogError(tag: String, text: String, e: Throwable) {
    if (DEBUG_MODE)
        Log.e(tag, text, e)
}

fun LogInfo(tag: String, text: String) {
    if (DEBUG_MODE)
        Log.i(tag, text)
}

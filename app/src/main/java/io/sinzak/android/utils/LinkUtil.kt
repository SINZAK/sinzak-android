package io.sinzak.android.utils

import android.content.Intent
import android.net.Uri
import io.sinzak.android.ui.base.BaseActivity

object LinkUtil {

    fun goToLink(
        activity: BaseActivity<*>,
        uriString : String
    ) {
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity.startActivity(intent)
    }
}
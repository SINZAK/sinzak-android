package io.sinzak.android.utils

import android.content.Intent
import android.net.Uri
import io.sinzak.android.ui.base.BaseActivity

object EmailUtil {

    fun goToWriteMail(
        activity: BaseActivity<*>,
    ){

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sinzakofficial@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT,"[신작 - 유저 문의]")
        }

        activity.startActivity(intent)


    }
}
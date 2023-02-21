package io.sinzak.android.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import io.sinzak.android.model.GlobalUiModel
import io.sinzak.android.ui.base.BaseActivity

object EmailUtil {

    fun goToWriteMail(
        activity: BaseActivity<*>,
        globalUiModel: GlobalUiModel
    ){

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("sinzakofficial@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT,"[신작] 문의하기")
        }

        try {
            activity.startActivity(intent)
        }
        catch (_: ActivityNotFoundException)
        {
            globalUiModel.showToast("이메일 앱을 찾을 수 없습니다")
        }

    }
}
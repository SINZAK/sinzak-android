package io.sinzak.android.ui.main.profile.certification

import android.net.Uri
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.FileUtil
import javax.inject.Inject

@ActivityRetainedScoped
class CertifyConnect @Inject constructor() {
    private lateinit var activity : BaseActivity<*>

    fun registerActivity(a: BaseActivity<*>)
    {
        activity = a
    }
    fun loadImage(callback : (Uri)->Unit)
    {
        FileUtil.pickFromGallery(activity, {callback(it[0])},false)
    }
}
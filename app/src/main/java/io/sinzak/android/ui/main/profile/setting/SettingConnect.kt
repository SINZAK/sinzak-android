package io.sinzak.android.ui.main.profile.setting

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.EmailUtil
import javax.inject.Inject

@ActivityRetainedScoped
class SettingConnect @Inject constructor() {

    private lateinit var activity: BaseActivity<*>

    fun registerActivity(a: BaseActivity<*>)
    {
        activity = a
    }

    fun goToWriteMail()
    {
        EmailUtil.goToWriteMail(activity)
    }
}
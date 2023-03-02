package io.sinzak.android.ui.main.profile.setting

import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.model.GlobalUiModel
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.utils.EmailUtil
import io.sinzak.android.utils.LinkUtil
import javax.inject.Inject

@ActivityRetainedScoped
class SettingConnect @Inject constructor() {

    private lateinit var activity: BaseActivity<*>

    fun registerActivity(a: BaseActivity<*>)
    {
        activity = a
    }

    fun goToWriteMail(globalUiModel: GlobalUiModel)
    {
        EmailUtil.goToWriteMail(activity, globalUiModel)
    }

    fun showLicense(activityTitle : String)
    {
        activity.startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
        OssLicensesMenuActivity.setActivityTitle(activityTitle)
    }

    fun connectLink(url : String){
        LinkUtil.goToLink(activity,url)
    }
}
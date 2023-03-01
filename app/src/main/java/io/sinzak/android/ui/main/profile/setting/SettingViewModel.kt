package io.sinzak.android.ui.main.profile.setting

import android.os.Looper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.BuildConfig
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_OAUTH_ORIGIN
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs
import kotlinx.coroutines.*
import java.util.logging.Handler

@HiltViewModel
class SettingViewModel @Inject constructor(
    val connect: SettingConnect
): BaseViewModel() {

    val socialOrigin get() = prefs.getString(CODE_OAUTH_ORIGIN,"")

    val version get() = BuildConfig.VERSION_NAME

    val resignSuccess get() = signModel.resignSuccessFlag

    /************************************************
     * 클릭 시 실행
     ***************************************/

    fun changePage(page: Page)
    {
        navigation.changePage(page)
    }

    /**
     * 문의하기를 누릅니다
     */
    fun onContactUs()
    {
        connect.goToWriteMail(uiModel)
    }

    /**
     * 오픈소스 라이선스를 누릅니다
     */
    fun onLicense()
    {
        connect.showLicense(valueModel.getString(R.string.str_setting_license))
    }

    /**
     * 회원 탈퇴를 누릅니다
     */
    fun onResign()
    {
        signModel.resign()
    }

    /**
     * 회원 탈퇴 후 확인을 누릅니다
     */
    fun completeResign()
    {
        navigation.removeHistory(Page.PROFILE_SETTING)
        navigation.changePage(Page.HOME)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            resignSuccess.value = false
        }

    }

    /**
     * 로그아웃을 누릅니다
     */
    fun logout(){
        signModel.logout()
        uiModel.navigation.changePage(Page.HOME)
    }

    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }
}
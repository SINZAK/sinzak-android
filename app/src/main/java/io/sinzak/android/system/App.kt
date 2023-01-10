package io.sinzak.android.system

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.content.edit
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.util.AndroidVer
import dagger.hilt.android.HiltAndroidApp
import io.sinzak.android.R
import io.sinzak.android.constants.KAKAO_NATIVE
import io.sinzak.android.constants.NAVER_CLIENT_ID
import io.sinzak.android.constants.NAVER_SECRET_ID
import io.sinzak.android.constants.getHashKey
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.remote.dataclass.local.SchoolData
import java.lang.RuntimeException
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var signModel: SignModel


    override fun onCreate() {
        super.onCreate()


        prefs = PreferenceUtil(this)

        initKaKaoSdk()
        initNaverSdk()

    }



    private fun initKaKaoSdk(){

        KakaoSdk.init(this, KAKAO_NATIVE)
        getHashKey(this)
    }

    private fun initNaverSdk(){
        try {
            NaverIdLoginSDK.initialize(
                this,
                NAVER_CLIENT_ID,
                NAVER_SECRET_ID,
                getString(R.string.str_app_name_kr)
            )
        }catch(e:Exception){
            val OAUTH_LOGIN_PREF_NAME_PER_APP  = "NaverOAuthLoginEncryptedPreferenceData"
            val preferences = getSharedPreferences(OAUTH_LOGIN_PREF_NAME_PER_APP, Context.MODE_PRIVATE)
            val OLD_OAUTH_LOGIN_PREF_NAME  = "NaverOAuthLoginPreferenceData"

            if (Build.VERSION.SDK_INT >= AndroidVer.API_24_NOUGAT) {
                deleteSharedPreferences(OLD_OAUTH_LOGIN_PREF_NAME)
            } else {
                preferences.edit {
                    clear()
                }
            }
            NAVER_SDK_PREPARED = false
        }
    }

    companion object{
        lateinit var prefs : PreferenceUtil

        var NAVER_SDK_PREPARED = true
    }



}
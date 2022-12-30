package io.sinzak.android.system

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import io.sinzak.android.R
import io.sinzak.android.constants.KAKAO_NATIVE
import io.sinzak.android.constants.NAVER_CLIENT_ID
import io.sinzak.android.constants.NAVER_SECRET_ID
import io.sinzak.android.constants.getHashKey

@HiltAndroidApp
class App : Application() {



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
        NaverIdLoginSDK.initialize(this, NAVER_CLIENT_ID, NAVER_SECRET_ID,getString(R.string.str_app_name_kr))
    }

    companion object{
        lateinit var prefs : PreferenceUtil
    }
}
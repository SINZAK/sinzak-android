package io.sinzak.android.system

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import io.sinzak.android.constants.KAKAO_NATIVE
import io.sinzak.android.constants.getHashKey

@HiltAndroidApp
class App : Application() {



    override fun onCreate() {
        super.onCreate()


        prefs = PreferenceUtil(this)

        initKaKaoSdk()
    }



    private fun initKaKaoSdk(){

        KakaoSdk.init(this, KAKAO_NATIVE)
        getHashKey(this)
    }

    companion object{
        lateinit var prefs : PreferenceUtil
    }
}
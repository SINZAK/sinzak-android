package io.sinzak.android.system

import android.app.Application
import android.content.res.Resources
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import io.sinzak.android.R
import io.sinzak.android.constants.KAKAO_NATIVE
import io.sinzak.android.constants.NAVER_CLIENT_ID
import io.sinzak.android.constants.NAVER_SECRET_ID
import io.sinzak.android.constants.getHashKey
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.remote.dataclass.local.SchoolData
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

        loadUnivList()
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


    fun loadUnivList(){

        val univ = resources.getStringArray(R.array.univ_name).toList()
        val domain = resources.getStringArray(R.array.univ_mail).toList()
        signModel.univList = univ.map{
            SchoolData(
                it,
                domain[univ.indexOf(it)]
            )
        }
    }
}
package io.sinzak.android.system.social

import com.navercorp.nid.oauth.OAuthLoginCallback
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import kotlinx.coroutines.flow.MutableStateFlow

class NaverImpl(
    private val loginSuccess: MutableStateFlow<Boolean>,
    private val loginFailed: MutableStateFlow<Boolean>,
    private val error: MutableStateFlow<String>
) : OAuthLoginCallback {
    override fun onError(errorCode: Int, message: String) {
        LogError(javaClass.name,"[NAVER LOGIN] $message")
        error.value = message
        loginSuccess.value = false
        loginFailed.value = true
    }

    override fun onFailure(httpStatus: Int, message: String) {
        LogError(javaClass.name,"[NAVER LOGIN] $message")
        loginFailed.value = true
        loginSuccess.value = false
    }

    override fun onSuccess() {
        LogInfo(javaClass.name,"[NAVER LOGIN] Login SuccesR")
        loginFailed.value = false
        loginSuccess.value = true
    }
}
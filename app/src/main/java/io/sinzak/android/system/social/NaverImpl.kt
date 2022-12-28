package io.sinzak.android.system.social

import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.flow.MutableStateFlow

class NaverImpl(
    private val loginSuccess: MutableStateFlow<Boolean>,
    private val loginFailed: MutableStateFlow<Boolean>,
    private val error: MutableStateFlow<String>
) : OAuthLoginCallback {
    override fun onError(errorCode: Int, message: String) {
        error.value = message
        loginSuccess.value = false
        loginFailed.value = true
    }

    override fun onFailure(httpStatus: Int, message: String) {
        loginFailed.value = true
        loginSuccess.value = false
    }

    override fun onSuccess() {
        loginFailed.value = false
        loginSuccess.value = true
    }
}
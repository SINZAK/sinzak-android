package io.sinzak.android.di

import io.sinzak.android.constants.TAG_REMOTE
import io.sinzak.android.system.LogInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetStatus @Inject constructor() {
    private val _onRemotePending = MutableStateFlow(false)
    val onRemotePending : StateFlow<Boolean> get() = _onRemotePending

    val lottieVisible = MutableStateFlow(false)


    private var pendingCallCnt = 0

    fun pendingUp(){
        pendingCallCnt ++
        if(!onRemotePending.value)
            delayLottieVisible()
        _onRemotePending.value = pendingCallCnt > 0

        LogInfo(TAG_REMOTE,"현재 $pendingCallCnt 개의 요청이 진행중입니다.")
    }

    fun pendingDown(){
        pendingCallCnt --
        _onRemotePending.value = pendingCallCnt > 0
        if(!onRemotePending.value)
            delayLottieInVisible()

        LogInfo(TAG_REMOTE,"현재 $pendingCallCnt 개의 요청이 진행중입니다.")
    }

    private fun delayLottieVisible(){
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            lottieVisible.value = onRemotePending.value
        }
    }

    private fun delayLottieInVisible(){
        CoroutineScope(Dispatchers.IO).launch {
            delay(50)
            lottieVisible.value = onRemotePending.value
        }
    }


}
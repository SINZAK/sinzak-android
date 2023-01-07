package io.sinzak.android.remote.retrofit

import io.sinzak.android.constants.ACCESS_TOKEN
import io.sinzak.android.constants.REFRESH_TOKEN
import io.sinzak.android.constants.TAG_REMOTE
import io.sinzak.android.di.NetStatus
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.response.login.Token
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.NotActiveException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Remote @Inject constructor(val remoteApi : RemoteInterface) : Callback<Result<CResponse>> {


    @Inject
    lateinit var netStatus: NetStatus

    override fun onFailure(call: Call<Result<CResponse>>, t: Throwable) {
        throw NotActiveException()
    }

    override fun onResponse(call: Call<Result<CResponse>>, response: Response<Result<CResponse>>) {


        netStatus.pendingDown()

        when{
            response.body() is Result.Success ->{
                onResultSuccess(response.body() as Result.Success)
            }

            response.body() is Result.LocalError ->{
                onLocalFailed(response.body() as Result.LocalError)
            }

            response.body() is Result.ServerError ->{
                onRemoteFailed(response.body() as Result.ServerError)
            }
        }
    }

    private fun onResultSuccess(result : Result.Success<CResponse>)
    {
        val body = result.body

        if(body is Token && body.success != false)
        {

            prefs.setString(ACCESS_TOKEN,body.accessToken)
            prefs.setString(REFRESH_TOKEN,body.refreshToken)
            LogInfo(TAG_REMOTE,"[TOKEN] ACCESS  TOKEN : ${prefs.accessToken}")
            LogInfo(TAG_REMOTE,"[TOKEN] REFRESH TOKEN : ${prefs.refreshToken}")

        }

        LogInfo(TAG_REMOTE,"[RESPONSE] Body를 받았습니다.")
        LogInfo(TAG_REMOTE,"$body")

        result.callback.onConnectionSuccess(result.apiNum,body)
    }

    private fun onRemoteFailed(result : Result.ServerError<CResponse>)
    {
        LogError(TAG_REMOTE,"서버 에러가 발생햇습니다.")
        LogError(TAG_REMOTE,"서버로부터의 메세지 : ")
        LogError(TAG_REMOTE,"${result.msg}")


        result.callback.handleError(result.apiNum,msg = result.msg, t = null)
    }

    private fun onLocalFailed(result : Result.LocalError<CResponse>)
    {
        LogError(TAG_REMOTE,"로컬 클라이언트 에러가 발생했습니다.")
        result.t?.let{
            LogError(it)
        }

        result.callback.handleError(result.apiNum,msg = null, t = result.t)
    }


    fun sendRequestApi(callData: CallImpl) {
        LogInfo(TAG_REMOTE, "통신을 요청합니다.")
        netStatus.pendingUp()
        CallExtent(
            callData.apiNum,
            callData.getCall(remoteApi),
            callData.callback,
            callData
        ).enqueue(this)
    }


}
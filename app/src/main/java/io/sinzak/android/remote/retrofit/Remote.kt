package io.sinzak.android.remote.retrofit

import io.sinzak.android.constants.TAG_REMOTE
import io.sinzak.android.di.NetStatus
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.NotActiveException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Remote @Inject constructor() : Callback<Result<CResponse>> {


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
        result.callback.onConnectionSuccess(result.apiNum,result.body)
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



    fun sendRequestApi(apiNum : Int, callback : RemoteListener)
    {
        LogInfo(TAG_REMOTE,"파라미터가 없는 통신 요청을 생성합니다.")
        LogInfo(TAG_REMOTE,"API 번호 : $apiNum")
        var call : Call<CResponse>? = null

        call?.let{
            sendRequestApi(apiNum,it,callback)
        }
    }


    private fun sendRequestApi(apiNum: Int,call : Call<CResponse>, callback : RemoteListener)
    {

        LogInfo(TAG_REMOTE,"통신을 요청합니다.")
        netStatus.pendingUp()
        CallExtent(apiNum,call,callback).enqueue(this)
    }

}
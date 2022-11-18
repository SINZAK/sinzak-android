package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse

sealed class Result<out T : CResponse>(
    open val apiNum: Int,
    open val callback : RemoteListener
){
    data class Success<T : CResponse>(override val apiNum : Int,
                                   override val callback: RemoteListener, val body : T) : Result<T>(apiNum, callback)
    data class ServerError<T : CResponse>(override val apiNum : Int,
                                       override val callback: RemoteListener, val msg : String)  : Result<T>(apiNum,callback)
    data class LocalError<T : CResponse>(override val apiNum : Int,
                                      override val callback: RemoteListener, val t : Throwable?)  : Result<T>(apiNum, callback)
}

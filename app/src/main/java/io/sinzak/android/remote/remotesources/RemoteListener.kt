package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.CResponse

interface RemoteListener{

    fun onConnectionSuccess(api : Int, body: CResponse)


    fun handleError(api : Int, msg : String?, t : Throwable?)
}
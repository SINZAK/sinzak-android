package io.sinzak.android.model

import io.sinzak.android.di.NetStatus
import io.sinzak.android.remote.retrofit.Remote
import io.sinzak.android.remote.retrofit.RemoteListener
import javax.inject.Inject

abstract class BaseModel : RemoteListener {

    @Inject
    lateinit var netStatus: NetStatus

    @Inject
    lateinit var remote : Remote

}
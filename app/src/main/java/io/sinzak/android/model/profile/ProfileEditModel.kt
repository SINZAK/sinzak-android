package io.sinzak.android.model.profile

import io.sinzak.android.constants.API_EDIT_MY_PROFILE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.UpdateUserRequest
import io.sinzak.android.remote.retrofit.CallImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileEditModel @Inject constructor() : BaseModel() {

    private var introduction = ""
    private var name = ""
    private var picture = ""


    fun updateProfile()
    {
        val request = UpdateUserRequest(
            introduction,
            name,
            picture
        )
        CallImpl(
            API_EDIT_MY_PROFILE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun setIntroduction(i : String){
        introduction = i
    }
    fun setName(n : String)
    {
        name = n
    }
    fun setPicture(p: String){
        picture = p
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_EDIT_MY_PROFILE ->
            {
                if (body.success == true)
                {
                    globalUi.showToast("정상적으로 편집완료")
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}
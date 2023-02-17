package io.sinzak.android.model.profile

import io.sinzak.android.R
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

    private var nameErrorMsg = ""

    /**
     * 소개를 저장합니다
     */
    fun setIntroduction(i : String){
        introduction = i.trim()
    }

    /**
     * 이름을 저장합니다 (앞뒤 공백없이)
     */
    fun setName(n : String)
    {
        name = n.trim()
    }

    /**
     * 프로필 이미지를 저장합니다
     */
    fun setPicture(p: String){
        picture = p
    }


    /**
     * 프로필을 업데이트합니다
     */
    fun updateProfile()
    {
        if (isNameValidate()) requestUpdate()
        else globalUi.showToast(nameErrorMsg)
    }

    /**
     * 닉네임 형식을 체크합니다
     */
    private fun isNameValidate() : Boolean
    {
        if (name.contains(' ')){
            nameErrorMsg = "닉네임은 공백없이 설정해주세요"
            return false
        }

        name.forEach {
            if (it.code !in 0xAC00..0xD7AF && it.code !in 0x41..0x5A && it.code !in 0x61..0x7A && it !in "._-" && it !in "0123456789"){
                nameErrorMsg = "닉네임 기호는 - _ . 만 사용 가능합니다"
                return false
            }
        }

        return true
    }

    /**
     * 프로필 편집을 요청합니다
     */
    private fun requestUpdate()
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


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_EDIT_MY_PROFILE ->
            {
                if (body.success == true)
                {
                    globalUi.showToast("프로필이 변경되었습니다")
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
        when(api)
        {
            API_EDIT_MY_PROFILE ->
            {

            }
        }
    }
}
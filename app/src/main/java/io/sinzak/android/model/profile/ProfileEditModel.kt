package io.sinzak.android.model.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.API_CHECK_NAME
import io.sinzak.android.constants.API_EDIT_MY_IMAGE
import io.sinzak.android.constants.API_EDIT_MY_INTEREST
import io.sinzak.android.constants.API_EDIT_MY_PROFILE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.UpdateInterestRequest
import io.sinzak.android.remote.dataclass.request.profile.UpdateUserRequest
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogDebug
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileEditModel @Inject constructor(
    @ApplicationContext val context : Context,
) : BaseModel() {

    private var introduction = ""
    private var name = ""
    private var nameErrorMsg = ""

    private var link = ""

    private var interest = ""

    private var multiPart : MultipartBody.Part? = null
    private val convertUriFlag = MutableStateFlow(false)
    val isEditDone = MutableStateFlow(false)

    val interestUpdateDone = MutableStateFlow(false)

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
     * 포트폴리오 링크를 저장합니다 (앞뒤 공백없이)
     */
    fun setLink(l : String)
    {
        link = l.trim()
    }

    /**
     * 관심장르를 저장합니다 (앞뒤 공백없이)
     */
    fun setInterest(i : String)
    {
        interest = i.trim()
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

        if(name.isEmpty()){
            nameErrorMsg = "닉네임은 한 글자 이상 설정해주세요"
            return false
        }

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
     * 이미지를 MultiPart 로 변환
     */
    fun convertUriToMultiPart(uri: Uri)
    {
        CoroutineScope(Dispatchers.IO).launch {
            if (!uri.toString().contains("http"))
            {
                val imgBitmap : Bitmap = FileUtil.getBitmapFile(context, uri)

                multiPart = FileUtil.getMultipart(context,"multipartFile",imgBitmap)

                convertUriFlag.value = true
            }

            else {
                convertUriFlag.value = false
            }
        }
    }

    /********************************
     * API 요청합니다
     ********************************/

    /**
     * 프로필 이미지 변경을 요청합니다
     */
    private fun requestChangeImage()
    {
        if (multiPart == null) {
            LogDebug(javaClass.name, "변환 실패")
            globalUi.showToast("다른 이미지를 사용해주세요")
            return
        }

        val requestBody = multiPart

        CallImpl(
            API_EDIT_MY_IMAGE,
            this,
            multipart = requestBody
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 프로필 편집을 요청합니다
     */
    private fun requestUpdate()
    {
        val request = UpdateUserRequest(
            introduction,
            name,
        )
        CallImpl(
            API_EDIT_MY_PROFILE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 관심장르 변경을 요청합니다
     */
    fun requestInterestUpdate()
    {

        val request = UpdateInterestRequest(
            categoryLike = interest
        )

        CallImpl(
            API_EDIT_MY_INTEREST,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 인증작가 신청을 요청합니다
     */
    fun requestVerify()
    {

    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {

            API_EDIT_MY_PROFILE ->
            {
                if (body.success == true)
                {
                    if (convertUriFlag.value) requestChangeImage()

                    else isEditDone.value = true

                }
            }

            API_EDIT_MY_IMAGE ->
            {
                if (body.success == true) isEditDone.value = true

                else globalUi.showToast("프로필 사진변경을 실패했어요")
            }

            API_EDIT_MY_INTEREST -> {

                if (body.success == true){
                    interestUpdateDone.value = true
                    globalUi.showToast("관심장르를 변경했어요")
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
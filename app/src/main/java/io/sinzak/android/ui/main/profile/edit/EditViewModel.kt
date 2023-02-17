package io.sinzak.android.ui.main.profile.edit

import android.content.res.Resources
import android.text.Editable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileEditModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    val model: ProfileEditModel,
    val pModel : ProfileModel,
    val connect: EditConnect
) : BaseViewModel() {

    /**
     * 내 프로필
     */
    val profile get() = pModel.profile

    /********************************
     * 소개를 저장하는 공간
     ********************************/
    private val _introduction = MutableStateFlow(profile.value!!.introduction)
    val introduction : StateFlow<String> get() = _introduction

    fun typeInputText(cs : CharSequence) {

        if(connect.bind.etIntro.lineCount > 4){
            connect.bind.etIntro.setText(_introduction.value)
            connect.bind.etIntro.setSelection(_introduction.value.length)
        }
        else {
            cs.toString().let {
                if (_introduction.value != it) {
                    _introduction.value = it
                    model.setIntroduction(it)
                }
            }
        }
    }

    fun inputName(cs: CharSequence)
    {
        model.setName(cs.toString())
    }

    /********************************
     * 버튼을 누릅니다
     ********************************/

    /**
     * 완료 버튼을 누릅니다
     */
    fun onSubmit()
    {
        model.updateProfile()
    }

    /**
     * 프로필 사진 바꾸기를 누릅니다
     */
    fun onClickChangeImg()
    {
        connect.loadImage {
            /*model.setPicture(it.toString())*/
            insertImgOnScreen(connect.bind.imgProfile,it.toString())
        }
    }

    /**
     * 학교 인증 버튼을 누릅니다
     */
    fun gotoCertificationPage(hasCert : Boolean){
        if(!hasCert) navigation.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }

    /**
     * 인증 작가 신청하기를 누릅니다
     */
    fun gotoVerifyPage(){
        navigation.changePage(Page.PROFILE_VERIFY)
    }
    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }

    /********************************
     * UI
     ********************************/

    /**
     * 화면에 이미지 삽입
     */
    private fun insertImgOnScreen(imageView : ImageView, uri : String )
    {

        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(imageView).asBitmap().load(uri)
                .transform(
                    CenterCrop(),
                    RoundedCorners(
                (Resources.getSystem().displayMetrics.density * 20f).toInt()
            )
            ).into(imageView)
        }
    }


}
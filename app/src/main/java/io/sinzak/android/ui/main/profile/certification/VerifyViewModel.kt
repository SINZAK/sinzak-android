package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(val pModel : ProfileModel) : BaseViewModel() {

    val profile get() = pModel.profile
    
    fun gotoCertificationPage(hasCerti : Boolean){
        if(!hasCerti) navigation.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }

}
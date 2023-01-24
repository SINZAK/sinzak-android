package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.adapter.ProfileArtAdapter
import javax.inject.Inject

@HiltViewModel
class ProfileWorkViewModel @Inject constructor(
    val model : ProfileModel
): BaseViewModel() {
//    val adapter = ProfileArtAdapter(type = 1)
//
//    init {
//        invokeStateFlow(model.workList){
//            adapter.setWorkData(it)
//        }
//    }
    val workList get() = model.workList
}
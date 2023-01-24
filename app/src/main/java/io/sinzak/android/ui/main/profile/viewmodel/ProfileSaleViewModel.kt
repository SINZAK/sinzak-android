package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.profile.UserProduct
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.adapter.ProfileArtAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileSaleViewModel @Inject constructor(
    val model : ProfileModel
) : BaseViewModel() {
//    val adapter = ProfileArtAdapter(type = 0)
//
//    init {
//        invokeStateFlow(model.productList){
//            adapter.setProductData(it)
//        }
//    }
    val productList get() = model.productList
}
package io.sinzak.android.ui.main.profile.setting

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.profile.UserProfile
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.setting.adapter.BlockListAdapter
import javax.inject.Inject

@HiltViewModel
class BlockListViewModel @Inject constructor(
    val model: ProfileModel
) : BaseViewModel() {

    val adapter = BlockListAdapter(::onUnBlockButton)

    init {
        adapter.apply {

        }

        val listTemp = listOf<UserProfile>(UserProfile(name = "유명", userId = "0"),UserProfile(name = "신작", userId = "1"),UserProfile(name = "호식이", userId = "2"))
        adapter.setBlockList(listTemp)
    }

    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 차단 해제를 누릅니다
     */
    fun onUnBlockButton(userId : String){

    }

    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }
}
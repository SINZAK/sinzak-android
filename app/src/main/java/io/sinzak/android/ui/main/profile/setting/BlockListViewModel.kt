package io.sinzak.android.ui.main.profile.setting

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.remote.dataclass.profile.UserProfile
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.setting.adapter.BlockListAdapter
import javax.inject.Inject

@HiltViewModel
class BlockListViewModel @Inject constructor(
    val model: UserCommandModel
) : BaseViewModel() {

    val adapter = BlockListAdapter(::onUnBlockButton)


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
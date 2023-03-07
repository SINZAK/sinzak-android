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

    init {
        invokeStateFlow(model.reportList){
            adapter.setBlockList(it)
        }
    }

    /************************************************
     * API 를 요청
     ***************************************/
    fun getReportList()
    {
        model.getReportList()
    }


    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 차단 해제를 누릅니다
     */
    private fun onUnBlockButton(userId : String){
        model.cancelReport(userId)
    }

    /**
     * 뒤로가기를 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }
}
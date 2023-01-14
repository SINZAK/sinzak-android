package io.sinzak.android.model.navigate

import android.os.Bundle
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass


@Singleton
class Navigation @Inject constructor() {

    private val _topPage = MutableStateFlow(Page.HOME)
    val topPage : StateFlow<Page> get() = _topPage

    private val pageHistory = mutableListOf<Page>()


    private val bundleStore = mutableMapOf<KClass<*>, Bundle>()
    private val _bundleInserted = MutableStateFlow(0)

    /**
     * 뷰모델에서 구독하여, 번들 값의 변화를 인식
     */
    val bundleInserted : StateFlow<Int> get() = _bundleInserted



    fun putBundleData(to: KClass<*>, bundle: Bundle){
        bundleStore[to] = bundle
        // 모든 뷰모델에 새 번들 추가를 notify
        _bundleInserted.value = _bundleInserted.value + 1
    }

    fun getBundleData(on : KClass<*>) : Bundle?{
        val bundle = bundleStore[on]
        bundleStore.remove(on)
        bundle?.let{
            // 번들 사용시 하나씩 카운트 감소
            _bundleInserted.value = _bundleInserted.value - 1
            if(bundleStore.isEmpty())
                _bundleInserted.value = 0
        }
        return bundle
    }



    fun changePage(page : Page)
    {
        if(_topPage.value != page) {
            pageHistory.add(_topPage.value)
            _topPage.value = page
        }
    }

    fun revealHistory() : Boolean{
        if(pageHistory.isNotEmpty())
        {
            _topPage.value = pageHistory.last()
            pageHistory.removeLast()

            return true
        }

        return false
    }

    fun removeHistory(page : Page)
    {
        pageHistory.remove(page)
    }

    fun clearHistory(){
        pageHistory.clear()
    }





}
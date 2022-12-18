package io.sinzak.android.model.navigate

import io.sinzak.android.enums.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigation @Inject constructor() {

    private val _topPage = MutableStateFlow(Page.HOME)
    val topPage : StateFlow<Page> get() = _topPage

    private val pageHistory = mutableListOf<Page>()

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
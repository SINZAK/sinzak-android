package io.sinzak.android.model.navigate


import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.system.LogDebug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RegisterNavigation @Inject constructor() {

    private val _topPage = MutableStateFlow(RegisterPage.PAGE_AGREEMENT)
    val topPage : StateFlow<RegisterPage> get() = _topPage

    private val pageHistory = mutableListOf<RegisterPage>()

    fun changePage(page : RegisterPage)
    {
        if(_topPage.value != page) {
            LogDebug(javaClass.name,"PAGE INPUT $page")
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

    fun removeHistory(page : RegisterPage)
    {
        pageHistory.remove(page)
    }

    fun clearHistory(){
        pageHistory.clear()
    }





}
package io.sinzak.android.model.navigate


import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.sinzak.android.enums.RegisterPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject



class RegisterNavigation @Inject constructor() {

    private val _topPage = MutableStateFlow(RegisterPage.PAGE_AGREEMENT)
    val topPage : StateFlow<RegisterPage> get() = _topPage

    private val pageHistory = mutableListOf<RegisterPage>()

    fun changePage(page : RegisterPage)
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

    fun removeHistory(page : RegisterPage)
    {
        pageHistory.remove(page)
    }

    fun clearHistory(){
        pageHistory.clear()
    }



    @dagger.Module
    @InstallIn(ActivityComponent::class)
    internal object Module{
        @Provides
        internal fun provideNavigation() : RegisterNavigation{
            return RegisterNavigation()
        }
    }

}
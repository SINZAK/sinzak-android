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

    fun changePage(page : Page)
    {
        if(_topPage.value != page)
            _topPage.value = page
    }
}
package io.sinzak.android.ui.login.interest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class InterestViewModel @Inject constructor(val regNav : RegisterNavigation) : BaseViewModel() {

    val genrePure = listOf("회화일반","동양화","조소","판화","공예","초상화","기타")
    val genreDesign = listOf("일러스트","패키지/라벨","인쇄물","포스터/배너/간판","로고/브랜딩","앱/웹 디자인")

    val chosenDesignChip = MutableStateFlow(mutableListOf<String>())
    fun putChosenDesign(genre : String)
    {
        val list = chosenDesignChip.value.toMutableList()
        if(list.contains(genre))
            list.remove(genre)
        else list.add(genre)
        chosenDesignChip.value = list
    }

    val chosenPureChip = MutableStateFlow(mutableListOf<String>())
    fun putChosenPure(genre : String)
    {
        val list = chosenPureChip.value.toMutableList()
        if(list.contains(genre))
            list.remove(genre)
        else list.add(genre)
        chosenPureChip.value = list
    }

    fun onSubmit(){

    }


}
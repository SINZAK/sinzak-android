package io.sinzak.android.ui.login.interest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.sign


@HiltViewModel
open class InterestViewModel @Inject constructor(
    val connect: RegisterConnect
) : BaseViewModel() {

    open val isPartOfRegister = true

    val genrePure = listOf("회화일반", "동양화", "조소", "판화", "공예", "초상화", "기타")
    val genreDesign = listOf("일러스트", "패키지/라벨", "인쇄물", "포스터/배너/간판", "로고/브랜딩", "앱/웹 디자인")

    val chosenDesignChip = MutableStateFlow(mutableListOf<String>())
    fun putChosenDesign(genre: String) : Boolean{
        val list = chosenDesignChip.value.toMutableList()
        if (list.contains(genre))
            list.remove(genre)
        else if (chosenDesignChip.value.size + chosenPureChip.value.size < 3)
            list.add(genre)
        else return false
        chosenDesignChip.value = list
        return true
    }

    val chosenPureChip = MutableStateFlow(mutableListOf<String>())
    fun putChosenPure(genre: String) : Boolean {
        val list = chosenPureChip.value.toMutableList()
        if (list.contains(genre))
            list.remove(genre)
        else if (chosenDesignChip.value.size + chosenPureChip.value.size < 3)
            list.add(genre)
        else return false
        chosenPureChip.value = list

        return true
    }

    open fun onSubmit() {
        signModel.setInterests(
            chosenDesignChip.value + chosenPureChip.value
        )
        connect.gotoUnivPage()
    }


    open fun onBackPressed(){
        connect.navigation.revealHistory()
    }


}
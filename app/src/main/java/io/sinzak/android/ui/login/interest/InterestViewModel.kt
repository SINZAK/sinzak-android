package io.sinzak.android.ui.login.interest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.GlobalValueModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
open class InterestViewModel @Inject constructor(
    val connect: RegisterConnect
) : BaseViewModel() {

    open val isPartOfRegister = true

    open val genrePure = valueModel.categoryMarket
    open val genreDesign = valueModel.categoryWork

    open val chosenDesignChip = MutableStateFlow(mutableListOf<String>())
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

    open val chosenPureChip = MutableStateFlow(mutableListOf<String>())
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
            valueModel.makeRequestStr(chosenDesignChip.value + chosenPureChip.value)
        )
        connect.gotoUnivPage()
    }


    open fun onBackPressed(){
        connect.navigation.revealHistory()
    }


}
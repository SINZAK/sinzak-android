package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileEditModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.login.RegisterConnect
import io.sinzak.android.ui.login.interest.InterestViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EditInterestViewModel @Inject constructor(
    val registerConnect: RegisterConnect,
    val model : ProfileEditModel,
    val pModel : ProfileModel
) : InterestViewModel(
    connect = registerConnect
) {

    override val isPartOfRegister: Boolean = false

    override val chosenPureChip: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf())

    override val chosenDesignChip: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf())

    private var initList = listOf<String>()

    init {
        setUserInterest()
    }

    /**
     * 기존 관심장르에 해당하는 칩들을 선택합니다
     */
    private fun setUserInterest()
    {
        val keys =  pModel.categoryLike.split(",")
        val pureList =chosenPureChip.value.toMutableList()
        val designList = chosenDesignChip.value.toMutableList()
        for (key in keys)
        {
            val value = valueModel.categoryMap[key].toString()
            if (genrePure.contains(value)) {
                pureList.add(value)
                continue
            }
            if (genreDesign.contains(value)) {
                designList.add(value)
                continue
            }
        }

        chosenPureChip.value = pureList
        chosenDesignChip.value = designList
        initList = chosenPureChip.value + chosenDesignChip.value

    }

    /**
     * 현재 관심장르와 기존 관심장르에 변화가 있는지?
     */
    private fun isInterestChange(initList : List<String>, currentList: List<String>) : Boolean {
        if (initList.size != currentList.size) return true

        val sortInitList = initList.sorted()
        val sortChangeList = currentList.sorted()

        for(i in sortInitList.indices){
            if (sortInitList[i] != sortChangeList[i]) return true
        }

        return false
    }



    override fun onSubmit() {

        val currentList = chosenPureChip.value + chosenDesignChip.value

        if (!isInterestChange(initList = initList, currentList = currentList)) onBackPressed()

        else {
            model.setInterest(valueModel.makeRequestStr(currentList))
            model.requestInterestUpdate()
            useFlag(model.interestUpdateDone){
                pModel.changeCategory(valueModel.makeRequestStr(currentList))
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        navigation.revealHistory()
    }
}
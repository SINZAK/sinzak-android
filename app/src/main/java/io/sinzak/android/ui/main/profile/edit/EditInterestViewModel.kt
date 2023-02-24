package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileEditModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.profile.UserProfile
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

    init {
        setUserInterest()
    }

    private fun setUserInterest()
    {
        val keys =  pModel.profile.value?.categoryLike.toString().split(",")
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

    }

    override fun onSubmit() {
        if (!model.interestUpdateFlag.value) onBackPressed()
        else {
            model.setInterest("")
            model.requestInterestUpdate()
            useFlag(model.interestUpdateDone){
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        navigation.revealHistory()
    }
}
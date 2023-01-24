package io.sinzak.android.ui.main.outsourcing

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.GlobalUiModel
import javax.inject.Inject


@ActivityRetainedScoped
class WorkConnect @Inject constructor(
    val uiConnect : GlobalUiModel
) {



    fun showSortDialog(init : Sort, callback : (Sort)->Unit){

       uiConnect.getActivity()?.apply{
           SortBottom().apply{
               show(supportFragmentManager, tag, init, callback)
           }

       }


    }


}
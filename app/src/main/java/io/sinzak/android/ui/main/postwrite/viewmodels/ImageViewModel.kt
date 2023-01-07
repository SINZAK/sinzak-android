package io.sinzak.android.ui.main.postwrite.viewmodels

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.postwrite.adapter.ImageAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor() : BaseViewModel() {

    val imgUris : MutableList<Uri> = mutableListOf()

    val adapter = ImageAdapter(imgUris)

    val imgCnt = MutableStateFlow(0)


    fun insertImg(uri : Uri)
    {
        if(imgUris.size > 4)
            imgUris.removeAt(0)

        imgUris.add(uri)

        imgCnt.value = imgUris.size

        adapter.notifyDataSetChanged()

    }

}
package io.sinzak.android.ui.main.outsourcing

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.FilterAdapter
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : BaseViewModel() {



    val adapter = FilterAdapter(mutableListOf("초상화","일러스트","로고/브랜딩","포스터/배너/간판","앱/웹 디자인","인쇄물","패키지/라벨","기타")) {

    }



}
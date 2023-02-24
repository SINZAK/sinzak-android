package io.sinzak.android.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.R
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GlobalValueModel @Inject constructor(@ApplicationContext val context: Context) {

    val univMap : Map<String, String> get() =  context.resources.getStringArray(R.array.univ_name).let{univ ->
        val mail = context.resources.getStringArray(R.array.univ_mail)
        val map = mutableMapOf<String,String>()
        for(i in univ.indices){
            map[univ[i]] = mail[i]
        }
        map
    }

    val categoryMarket = context.resources.getStringArray(R.array.category_market).toList()

    val categoryMarketDraw = listOf(
        R.drawable.ic_category_normal,
        R.drawable.ic_category_orient,
        R.drawable.ic_category_carv,
        R.drawable.ic_category_rawdraw,
        R.drawable.ic_category_arch,
        R.drawable.ic_category_other
    ).map{
        context.getDrawable(it)
    }

    val categoryWork = context.resources.getStringArray(R.array.category_work).toList()

    val categoryMap : Map<String, String> get() = context.resources.getStringArray(R.array.category_key).let { category ->
        val value = context.resources.getStringArray(R.array.category_value)
        val map = mutableMapOf<String, String>()
        for (i in category.indices){
            map[category[i]] = value[i]
        }
        map
    }

    /**
     * 키 형태의 관심장르를 한글로 바꿉니다
     */
    fun getCategory(categoryLike : String) : String
    {
        val keys = categoryLike.split(",")
        val resultList = mutableListOf<String>()
        for (key in keys)
        {
            categoryMap[key]?.let { resultList.add(it) }
        }
        return resultList.joinToString(", ")
    }

    private val reverseCategoryMap : Map<String, String> get() = context.resources.getStringArray(R.array.category_value).let { category ->
        val value = context.resources.getStringArray(R.array.category_key)
        val map = mutableMapOf<String, String>()
        for (i in category.indices){
            map[category[i]] = value[i]
        }
        map
    }

    /**
     * 선택된 관심장르를 카테고리 키 형태로 바꿉니다
     */
    fun makeRequestStr(list: List<String>) : String
    {
        val changeList = mutableListOf<String>()
        list.forEach {
            changeList.add(reverseCategoryMap[it].toString())
        }
        return changeList.joinToString(",")
    }

    fun getString(resourceId : Int)
        = context.getString(resourceId)



}
package io.sinzak.android.ui.main.profile.certification.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderCertificationSearchBinding
import io.sinzak.android.remote.dataclass.local.SchoolData

class CertificationAdapter(
    val onItemClick : ((name: SchoolData)->Unit)? = null,
) : RecyclerView.Adapter<CertificationAdapter.ViewHolder>() {

    private var schoolList = listOf<SchoolData>()
    private var input = ""

    fun setSchoolList(list : List<SchoolData>,input: String){
        schoolList = list
        this.input = input
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.holder_certification_search,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schoolList[position])
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }



    inner class ViewHolder(val bind: HolderCertificationSearchBinding) : RecyclerView.ViewHolder(bind.root)
    {

        fun bind(school : SchoolData)
        {
           bind.apply {
               tvSchoolItem.text = makeStringHighLight(school.schoolName,input)
               root.setOnClickListener {
                    onItemClick?.let { onItemClick -> onItemClick(school) }
               }
           }
        }
    }

    private fun makeStringHighLight(schoolName : String, input : String) : SpannableString
    {
        val highLightString = SpannableString(schoolName)
        val start : Int = schoolName.indexOf(input)
        val end: Int = start + input.length

        highLightString.setSpan(ForegroundColorSpan(Color.parseColor("#FF4040")),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return highLightString
    }




}
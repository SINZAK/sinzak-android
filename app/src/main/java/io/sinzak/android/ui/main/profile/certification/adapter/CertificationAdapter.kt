package io.sinzak.android.ui.main.profile.certification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderCertificationSearchBinding
import io.sinzak.android.remote.dataclass.local.SchoolData

class CertificationAdapter(val onItemClick : ((name: SchoolData)->Unit)? = null) : RecyclerView.Adapter<CertificationAdapter.ViewHolder>() {

    private var schoolList = listOf<SchoolData>()

    fun setSchoolList(list : List<SchoolData>){
        schoolList = list
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
               name = school.schoolName

               root.setOnClickListener {
                    onItemClick?.let { onItemClick -> onItemClick(school) }
               }
           }
        }
    }




}
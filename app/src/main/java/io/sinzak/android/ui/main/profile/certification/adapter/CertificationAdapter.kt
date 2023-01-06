package io.sinzak.android.ui.main.profile.certification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderCertificationSearchBinding

class CertificationAdapter(val onItemClick : ((name: String)->Unit)? = null) : RecyclerView.Adapter<CertificationAdapter.ViewHolder>() {

    private val schoolList = listOf(
        "남서울대학교",
        "동서울대학교",
        "서울대학교"
    )

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

        fun bind(school : String)
        {
           bind.apply {
               name = school

               root.setOnClickListener {
                    onItemClick?.let { onItemClick -> onItemClick(school) }
               }
           }
        }
    }




}
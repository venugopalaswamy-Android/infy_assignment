package com.android.infyassignment.ui.facts

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.infyassignment.ApplicationLevel
import com.android.infyassignment.R
import com.android.infyassignment.data.model.ClsFacts
import com.bumptech.glide.Glide

class FactsListAdapter(private val context: Context,private val listOfFacts: List<ClsFacts>): RecyclerView.Adapter<FactsListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val txt_title = itemView.findViewById(R.id.txt_title) as TextView
        val txt_description = itemView.findViewById(R.id.txt_description) as TextView
        val img_profile = itemView.findViewById(R.id.img_Profile) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.facts_list_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
      return listOfFacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fact : ClsFacts = listOfFacts[position]
        if(ApplicationLevel.isNullOrEmpty(fact.title)){
            holder.txt_title.text = context.getString(R.string.no_title)
        }else{
            holder.txt_title.text = fact.title
        }
        if(ApplicationLevel.isNullOrEmpty(fact.description)){
            holder.txt_description.text = context.getString(R.string.no_description)
        }else{
            holder.txt_description.text = fact.description
        }
        Glide
            .with(context)
            .load(fact.imageHref)
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .into(holder.img_profile)
    }



}
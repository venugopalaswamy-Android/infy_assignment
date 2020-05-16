package com.android.infyassignment.ui.facts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.infyassignment.R
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.utilities.Common
import com.bumptech.glide.Glide

class FactsListAdapter(
    private val context: Context,
    private var listOfFacts: MutableList<ClsFacts>
) : RecyclerView.Adapter<FactsListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById(R.id.txt_title) as TextView
        val txtDescription = itemView.findViewById(R.id.txt_description) as TextView
        val imgProfile = itemView.findViewById(R.id.img_Profile) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.facts_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listOfFacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fact: ClsFacts = listOfFacts[position]
        if (Common.isNullOrEmpty(fact.title)) {
            holder.txtTitle.text = context.getString(R.string.no_title)
        } else {
            holder.txtTitle.text = fact.title
        }
        if (Common.isNullOrEmpty(fact.description)) {
            holder.txtDescription.text = context.getString(R.string.no_description)
        } else {
            holder.txtDescription.text = fact.description
        }
        Glide
            .with(context)
            .load(fact.imageHref)
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .into(holder.imgProfile)
    }



}
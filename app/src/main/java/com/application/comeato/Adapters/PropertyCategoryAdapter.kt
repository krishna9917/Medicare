package com.application.comeato.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.R
import com.application.comeato.databinding.LayoutPropertyCategoryBinding

class PropertyCategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var tabSelected = 0

   private class PropertyCategoryViewHolder(val binding:LayoutPropertyCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  PropertyCategoryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_property_category,parent,false))
    }

    override fun getItemCount(): Int
    {
     return 5
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int)
    {
        val holder=viewHolder as PropertyCategoryViewHolder
        holder.binding.selected = position == tabSelected
        holder.binding.cvItem.setOnClickListener {
            tabSelected = position
            notifyDataSetChanged()
        }
    }


}
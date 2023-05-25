package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.R
import com.application.comeato.databinding.LayoutOffersItemBinding

class OffersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    class OffersViewHolder(val binding: LayoutOffersItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  OffersViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_offers_item,parent,false))
    }

    override fun getItemCount(): Int
    {
      return 5
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        val holder=viewHolder as OffersViewHolder
        holder.binding.context=holder.itemView.context

    }


}
package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Dialog.ShowDetails
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.LayoutOffersItemBinding
import com.application.comeato.models.Offer
import com.comeato.Utilities.MyApp

class OffersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var offersList:ArrayList<Offer>?=null

    class OffersViewHolder(val binding: LayoutOffersItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  OffersViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_offers_item,parent,false))
    }

    override fun getItemCount(): Int
    {
      return offersList?.size?:0
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        val holder=viewHolder as OffersViewHolder
        holder.binding.context=holder.itemView.context
        holder.binding.offerData= offersList?.get(position) ?: null
        holder.binding.txtTermsCondition.setOnClickListener{
            ShowDetails(
                holder.itemView.context,
                MyApp.MySingleton.getAppContext()
                    .getString(R.string.offers_terms_conditions),
                offersList?.get(position)?.terms_and_conditions!!
            ).show()
        }


    }

    public fun submitList(offersList:ArrayList<Offer>)
    {
        this.offersList=offersList
        notifyDataSetChanged()
    }


}
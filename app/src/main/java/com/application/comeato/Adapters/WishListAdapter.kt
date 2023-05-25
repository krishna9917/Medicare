package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutTopBrandPropertyBinding
import com.application.comeato.databinding.LayoutWishListBinding
import com.application.comeato.models.WishListData

class WishListAdapter(val clickListener: AdapterClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var wishList:List<WishListData>?=null

    private class WishListViewHolder(val binding: LayoutWishListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WishListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_wish_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int
    {
      return wishList?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        val holder = viewHolder as WishListViewHolder
        holder.binding.showShimmer=false
        holder.binding.wishlistProperty= wishList!![position]
        holder.binding.cvItem.setOnClickListener{
            clickListener.onClick(wishList!![position],position,101)
        }
        holder.binding.cvWishListBtn.setOnClickListener{
            clickListener.onClick(wishList!![position],position,102)
        }

    }

    public fun submitList(wishList:List<WishListData>)
    {
        this.wishList=wishList
        notifyDataSetChanged()
    }

}
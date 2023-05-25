package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutAccountTabsBinding
import com.application.comeato.models.MyAccountTabs

class MyAccountTabAdapter(val clickListener: AdapterClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tabs = ArrayList<MyAccountTabs>()

    private class TabViewHolder(val itemViewBinding: LayoutAccountTabsBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TabViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.layout_account_tabs, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as TabViewHolder
        holder.itemViewBinding.tabData = tabs[position]
        holder.itemViewBinding.context=holder.itemView.context
        holder.itemView.setOnClickListener {
            clickListener.onClick(tabs[position], position)
        }
    }

    override fun getItemCount(): Int {
        return tabs.size
    }


   public fun submitList(list: ArrayList<MyAccountTabs>)
    {
        tabs = list
        notifyDataSetChanged()
    }


}
package com.application.comeato.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutPropertyCategoryBinding
import com.application.comeato.models.Categories
import com.application.comeato.models.Category

class PropertyCategoryAdapter(val clickListener: AdapterClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var tabSelected = 0

    private  var categoriesList:ArrayList<Category>?= null

   private class PropertyCategoryViewHolder(val binding:LayoutPropertyCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  PropertyCategoryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_property_category,parent,false))
    }

    override fun getItemCount(): Int
    {
        if(categoriesList==null) return 5
        return categoriesList!!.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int)
    {
        val holder=viewHolder as PropertyCategoryViewHolder

        if(categoriesList!=null && categoriesList?.size!! >0)
        {
            if(position==tabSelected )
            {
                clickListener.onClick(categoriesList!![position],position,101)
                holder.binding.selected=true
            }else
            {
                holder.binding.selected=false
            }
            holder.binding.categoryData = categoriesList!![position]
            holder.binding.cvItem.setOnClickListener {
                if(tabSelected!=position)
                {
                    tabSelected = position
                    notifyDataSetChanged()
                }
            }
        }

    }

    public fun submitList(categoriesList:ArrayList<Category>)
    {
        this.categoriesList=categoriesList
        notifyDataSetChanged()
    }

}
package com.application.comeato.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutPropertyItem2Binding
import com.application.comeato.models.FeaturedProperty
import com.application.comeato.models.Property

class PropertyAdapter(val clickListener:AdapterClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var propertiesList =ArrayList<FeaturedProperty>()
    private class PropertyViewHolder(val itemBinding: LayoutPropertyItem2Binding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PropertyViewHolder( DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_property_item2,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
       val holder=viewHolder as PropertyViewHolder
        holder.itemBinding.property = propertiesList[position]
        holder.itemBinding.cvDetailCard.setOnClickListener{
            clickListener.onClick(propertiesList[position],position,102)
        }
    }

    override fun getItemCount(): Int
    {
        return propertiesList.size
    }

   public fun submitList(propertiesList:ArrayList<FeaturedProperty>)
   {
         this.propertiesList=propertiesList
         notifyDataSetChanged()
   }
    public fun addList(propertiesList:ArrayList<FeaturedProperty>)
    {
        this.propertiesList.addAll(propertiesList)
        notifyItemRangeChanged(this.propertiesList.size,propertiesList.size)
    }
}
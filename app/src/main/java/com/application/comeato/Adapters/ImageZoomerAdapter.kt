package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutImageZoomerItemBinding
import com.application.comeato.databinding.LayoutImagesListItemBinding
import com.application.comeato.models.ImageData

class ImageZoomerAdapter(private val layoutType: Int, private val imageList: ArrayList<String>, val clickListener: AdapterClickListener, var selected:Int=0) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class ImageZoomerViewHolder(val binding: LayoutImageZoomerItemBinding) : RecyclerView.ViewHolder(binding.root)
    private class ImageListViewHolder(val binding:LayoutImagesListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (layoutType) {
            0 -> ImageZoomerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.layout_image_zoomer_item,
                    parent,
                    false
                )
            )
            1-> ImageListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_images_list_item,parent,false))

            else -> ImageZoomerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_image_zoomer_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int
    {
        return when (layoutType)
        {
            0,1 -> imageList.size
            else -> 0
        }

    }

    override fun getItemViewType(position: Int): Int
    {
        return layoutType
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        when(layoutType)
        {
            0 -> {
                 val holder = viewHolder as ImageZoomerViewHolder
                  holder.binding.imageData=imageList[position]
             }
            1->{
                val holder = viewHolder as ImageListViewHolder
                holder.binding.selected=selected==position
                holder.binding.imageData= imageList[position]
                holder.itemView.setOnClickListener {
                    setSelectedItem(position)
                    clickListener.onClick(imageList[position],position,101)
                }
            }
        }
    }
    public fun setSelectedItem(selectedItem: Int)
    {
        notifyItemChanged(this.selected)
        this.selected=selectedItem
        notifyItemChanged(selectedItem)
    }

}
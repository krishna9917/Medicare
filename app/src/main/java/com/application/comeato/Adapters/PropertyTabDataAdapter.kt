package com.application.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.LayoutDealItemBinding
import com.application.comeato.databinding.LayoutGalleryImagesBinding
import com.application.comeato.models.ImageData

class PropertyTabDataAdapter(clickListener:AdapterClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var colors= intArrayOf(R.color.color_1,R.color.color_2,R.color.color_3,R.color.color_4,R.color.color_5,R.color.color_6,R.color.color_7)
  companion object
  {
      private var colorPosition = -1
  }
    private var layoutType:Int=0
    private var galleryList=ArrayList<ImageData>()


    class DealsViewHolder(val binding: LayoutDealItemBinding) : RecyclerView.ViewHolder(binding.root)
    class GalleryViewHolder(val binding: LayoutGalleryImagesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
     return  when(viewType)
       {
           0-> DealsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_deal_item,parent,false))
           1->GalleryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.layout_gallery_images,parent,false))
           else -> DealsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_deal_item,parent,false))
       }
    }

    override fun getItemCount(): Int
    {
      return  when(layoutType)
        {
            0->5
            1->galleryList.size
          else -> 0
      }
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        when(viewHolder.itemViewType)
        {
            0->{
                val holder =viewHolder as DealsViewHolder
                if(colorPosition<colors.size-1)
                {
                    ++colorPosition
                }else
                {
                    colorPosition=0
                }
                holder.binding.context=holder.itemView.context
                holder.binding.color=colors[colorPosition]
                UtilsFunction.setAnimation(viewHolder.itemView.context,viewHolder.itemView,R.anim.animation_slidefast_in_right)
            }
            1->{
                val holder = viewHolder as GalleryViewHolder
                holder.binding.image =galleryList[position]
                holder.itemView.setOnClickListener {

                }
                UtilsFunction.setAnimation(viewHolder.itemView.context,viewHolder.itemView,R.anim.animation_fall_down)
            }
        }
    }

    public fun submitLayout( layoutType:Int,galleryList: ArrayList<ImageData>?=ArrayList())
    {
        this.galleryList = galleryList!!
        this.layoutType=layoutType
        notifyDataSetChanged()
    }

}
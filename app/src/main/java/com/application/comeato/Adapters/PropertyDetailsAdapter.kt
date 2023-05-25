package com.application.comeato.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Dialog.ImageZoomer
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.LayoutImageViewItemBinding
import com.application.comeato.databinding.LayoutPropertyItem3Binding
import com.application.comeato.databinding.LayoutPropertyTabsBinding
import com.application.comeato.models.PropertyData
import com.application.comeato.models.PropertyDetail


class PropertyDetailsAdapter(private val layoutType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

   private lateinit var clickListener:AdapterClickListener
    constructor(clickListener:AdapterClickListener ,layoutType: Int) : this(layoutType)
    {
        this.clickListener=clickListener
    }

    private var propertyDetails: PropertyDetail? = null

    private var tabSelected = 0

    class ImageSlider(val binding: LayoutImageViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PropertyTabs(val binding: LayoutPropertyTabsBinding) :
        RecyclerView.ViewHolder(binding.root)


    private class SimilarPropertyViewHolder(val similarProperty: LayoutPropertyItem3Binding) :
        RecyclerView.ViewHolder(similarProperty.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> ImageSlider(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_image_view_item,
                    parent,
                    false
                )
            )

            2 -> PropertyTabs(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_property_tabs,
                    parent,
                    false
                )

            )

            3 -> SimilarPropertyViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_property_item3,
                    parent,
                    false
                )
            )
            else -> ImageSlider(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_image_view_item,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemCount(): Int {
        if(propertyDetails==null) return 0
        return when (layoutType) {
            1 -> propertyDetails?.property?.gallery!!.size
            2 -> propertyDetails?.property?.tabs!!.size
            3 -> propertyDetails?.property?.similar_properties!!.size
            else -> 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (viewHolder.itemViewType) {
            1 -> {
                val holder = viewHolder as ImageSlider
                holder.binding.image= propertyDetails?.property!!.gallery[position]
                UtilsFunction.setAnimation(
                    holder.itemView.context,
                    holder.itemView,
                    R.anim.animation_slidefast_in_right
                )
                holder.itemView.setOnClickListener {
                    ImageZoomer(holder.itemView.context, propertyDetails!!.property.gallery as ArrayList<String>,position).show()

                }
            }

            2 -> {
                val holder = viewHolder as PropertyTabs
                holder.binding.tab = propertyDetails?.property!!.tabs[position]
                holder.binding.selected = position == tabSelected
                holder.binding.cvItem.setOnClickListener {
                    if(tabSelected!=position)
                    {
                        notifyItemChanged(tabSelected)
                        tabSelected = position
                        clickListener.onClick(propertyDetails?.property!!.tabs[position],position,101)
                        notifyItemChanged(tabSelected)
                    }
                }
            }

            3 -> {
                val holder = viewHolder as SimilarPropertyViewHolder
                holder.similarProperty.property = propertyDetails?.property!!.similar_properties[position]
                UtilsFunction.setAnimation(
                    holder.itemView.context,
                    holder.itemView,
                    R.anim.animation_fall_down
                )
                holder.similarProperty.cvItem.setOnClickListener {
                    clickListener.onClick(propertyDetails?.property!!.similar_properties[position],position,103)
                }
            }
        }
    }

    public fun submitData(propertyDetails: PropertyDetail) {
        this.propertyDetails = propertyDetails
        notifyDataSetChanged()
    }


}
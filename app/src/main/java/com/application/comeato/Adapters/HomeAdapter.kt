package com.comeato.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.*
import com.application.comeato.models.HomeData

class HomeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var  clickListener:AdapterClickListener

    constructor(clickListener:AdapterClickListener) : this()
    {
        this.clickListener=clickListener
    }

    private var homeData: HomeData = HomeData()
    private var layoutType = 0

    private var colors= intArrayOf(R.color.color_1,R.color.color_2,R.color.color_3,R.color.color_4,R.color.color_5,R.color.color_6,R.color.color_7)
    var colorPosition = -1

    private class BannerViewHolder(val homeBannerBinding: LayoutHomeBannerBinding) : RecyclerView.ViewHolder(homeBannerBinding.root)
    private class CategoryViewHolder(val categoryBinding: LayoutCategoryBinding) : RecyclerView.ViewHolder(categoryBinding.root)
    private class NearPropertyViewHolder(val nearDealProperty: LayoutPropertyItemBinding) : RecyclerView.ViewHolder(nearDealProperty.root)
    private class TopBrandViewHolder(val layoutTopBrandPropertyBinding: LayoutTopBrandPropertyBinding) : RecyclerView.ViewHolder(layoutTopBrandPropertyBinding.root)
    private class DayDealViewHolder(val layDayDeals: LayoutPropertyItem1Binding) : RecyclerView.ViewHolder(layDayDeals.root)
    private class NearLocationViewHolder(val nearLocationItemBinding: LayoutNearLocationItemBinding) : RecyclerView.ViewHolder(nearLocationItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return when (viewType) {
            0,2 -> BannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_home_banner,
                    parent,
                    false
                )
            )
            1-> CategoryViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_category,
                    parent,
                    false
                )
            )
            3-> NearPropertyViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_property_item,
                    parent,
                    false
                )
            )
            4-> TopBrandViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_top_brand_property,
                    parent,
                    false
                )
            )
            5-> DayDealViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_property_item1,
                    parent,
                    false
                )
            )
            6-> NearLocationViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_near_location_item,
                parent,
                false
            ))
            else -> BannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_home_banner,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            0,2 -> {
                val holder: BannerViewHolder = viewHolder as BannerViewHolder
                holder.homeBannerBinding.banner =  if(viewHolder.itemViewType==0) homeData.topBanner[position] else homeData.brandBanner[position]
            }
            1->{
                val holder: CategoryViewHolder = viewHolder as CategoryViewHolder
                holder.categoryBinding.category = homeData.category[position]
                UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_bounce)
            }
            3->{
                val holder: NearPropertyViewHolder = viewHolder as NearPropertyViewHolder
                holder.nearDealProperty.property = homeData.nearDeals[position]
                UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_fall_down)
                holder.nearDealProperty.cvItem.setOnClickListener {
                    clickListener.onClick(homeData.nearDeals[position], position,layoutType)
                }
            }
            4->{
                val holder: TopBrandViewHolder = viewHolder as TopBrandViewHolder
                holder.layoutTopBrandPropertyBinding.property = homeData.topBrand[position]
                holder.layoutTopBrandPropertyBinding.cvItem.setOnClickListener {
                    clickListener.onClick(homeData.topBrand[position], position,layoutType)
                }
            }
            5->{
                val holder: DayDealViewHolder = viewHolder as DayDealViewHolder
                holder.layDayDeals.property = homeData.dayDeals[position]
                UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_slide_in_right)
                holder.layDayDeals.cvItem.setOnClickListener {
                    clickListener.onClick(homeData.dayDeals[position], position,layoutType)
                }
            }
            6->{
                val holder: NearLocationViewHolder = viewHolder as NearLocationViewHolder
                holder.nearLocationItemBinding.nearLocation=homeData.nearLocations[position]
                if(colorPosition<colors.size)
                {
                    colorPosition++
                }else
                {
                    colorPosition=0
                }
                holder.nearLocationItemBinding.context=holder.itemView.context
                holder.nearLocationItemBinding.textColor=colors[colorPosition]
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }


    override fun getItemCount(): Int
    {
        return when (layoutType) {
            0 -> homeData.topBanner.size
            1 -> homeData.category.size
            2 -> homeData.brandBanner.size
            3 -> homeData.nearDeals.size
            4 -> homeData.topBrand.size
            5 -> homeData.dayDeals.size
            6->  homeData.nearLocations.size
            else -> 0
        }

    }

    public fun submitList(homeData: HomeData, layout: Int)
    {
        this.homeData = homeData
        this.layoutType = layout
        notifyDataSetChanged()
    }



}
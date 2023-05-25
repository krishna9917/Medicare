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

class HomeAdapter(val layoutType :Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    lateinit var  clickListener:AdapterClickListener

    constructor(layoutType:Int,clickListener:AdapterClickListener) : this(layoutType)
    {
        this.clickListener=clickListener
    }

    private  var homeData: HomeData? =null

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
                if(homeData==null)
                {
                    holder.homeBannerBinding.showShimmer=true
                }else
                {
                    holder.homeBannerBinding.showShimmer=false
                    holder.homeBannerBinding.banner =  if(viewHolder.itemViewType==0) homeData?.home_slider!![position] else homeData?.brand_slider!![position]
                }
            }
            1->{
                val holder: CategoryViewHolder = viewHolder as CategoryViewHolder
                if(homeData==null) {
                    holder.categoryBinding.showShimmer = true
                }else
                {
                    holder.categoryBinding.showShimmer = false
                    holder.categoryBinding.category = homeData?.categories!![position]
                    UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_bounce)
                    holder.categoryBinding.cvItem.setOnClickListener{
                        clickListener.onClick(homeData?.categories!![position], position,layoutType)
                    }

                }

            }
            3->{
                val holder: NearPropertyViewHolder = viewHolder as NearPropertyViewHolder
                if(homeData==null)
                {
                    holder.nearDealProperty.showShimmer=true
                }else
                {
                    holder.nearDealProperty.showShimmer=false
                    holder.nearDealProperty.property = homeData?.featured_properties!![position]
                    UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_fall_down)
                    holder.nearDealProperty.cvItem.setOnClickListener {
                        clickListener.onClick(homeData?.featured_properties!![position], position,layoutType)
                    }
                }
            }
            4->{
                val holder: TopBrandViewHolder = viewHolder as TopBrandViewHolder
                if(homeData==null)
                {
                    holder.layoutTopBrandPropertyBinding.showShimmer=true
                }else
                {
                    holder.layoutTopBrandPropertyBinding.showShimmer=false
                    holder.layoutTopBrandPropertyBinding.property = homeData?.latest_properties!![position]
                    holder.layoutTopBrandPropertyBinding.cvItem.setOnClickListener {
                        clickListener.onClick(homeData?.latest_properties!![position], position,layoutType)
                    }
                }

            }
            5->{
                val holder: DayDealViewHolder = viewHolder as DayDealViewHolder
                if(homeData==null)
                {
                    holder.layDayDeals.showShimmer=true
                }else
                {
                    holder.layDayDeals.showShimmer=false
                    holder.layDayDeals.property = homeData?.special_deals!![position]
                    UtilsFunction.setAnimation(holder.itemView.context, holder.itemView, R.anim.animation_slide_in_right)
                    holder.layDayDeals.cvItem.setOnClickListener {
                        clickListener.onClick(homeData?.special_deals!![position], position,layoutType)
                    }
                }

            }
            6->{
                val holder: NearLocationViewHolder = viewHolder as NearLocationViewHolder
                if(homeData==null)
                {
                    holder.nearLocationItemBinding.showShimmer=true
                }else
                {
                    holder.nearLocationItemBinding.showShimmer=false
                    holder.nearLocationItemBinding.nearLocation= homeData?.near_by_locations!![position]
                }
                if(colorPosition<colors.size-1)
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
        if(homeData==null)
        {
           return when(layoutType)
            {
                0 -> 1
                1 -> 8
                2 -> 1
                3 -> 5
                4 -> 4
                5 -> 6
                6 -> 4
                else -> 0
            }
        }

        return when (layoutType)
        {
            0 -> homeData?.home_slider?.size!!
            1 -> homeData?.categories?.size!!
            2 -> homeData?.brand_slider?.size!!
            3 -> homeData?.featured_properties?.size!!
            4 -> homeData?.latest_properties?.size!!
            5 -> homeData?.special_deals?.size!!
            6-> homeData?.near_by_locations?.size!!
            else -> 0
        }

    }

    public fun submitList(homeData: HomeData)
    {
        this.homeData = homeData

        notifyDataSetChanged()
    }



}
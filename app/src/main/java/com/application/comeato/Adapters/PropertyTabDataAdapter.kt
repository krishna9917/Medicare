package com.application.comeato.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.Dialog.ImageZoomer
import com.application.comeato.Dialog.ShowAlert
import com.application.comeato.Dialog.ShowDetails
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.Interfaces.ShowDealListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.LayoutDealItemBinding
import com.application.comeato.databinding.LayoutGalleryImagesBinding
import com.application.comeato.models.Deal
import com.application.comeato.models.DealCode
import com.comeato.Utilities.MyApp
import com.google.gson.Gson

class PropertyTabDataAdapter(val clickListener: AdapterClickListener,val showDealListener: ShowDealListener,val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),DialogClickListener {
    private var colors = intArrayOf(
        R.color.color_1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5,
        R.color.color_6,
        R.color.color_7
    )

    private var colorPosition = -1

    private var layoutType: Int = 0

    private var tabData: ArrayList<Any>? = null
    private val gson = Gson()

    class DealsViewHolder(val binding: LayoutDealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class GalleryViewHolder(val binding: LayoutGalleryImagesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> DealsViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_deal_item,
                    parent,
                    false
                )
            )

            2 -> GalleryViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_gallery_images,
                    parent,
                    false
                )
            )

            else -> DealsViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_deal_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        if (tabData == null) return 0
        return tabData?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return layoutType
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        when (viewHolder.itemViewType) {
            1 -> {
                val jsonString = gson.toJson(tabData!![position])
                val dealData = gson.fromJson(jsonString, Deal::class.java)
                val holder = viewHolder as DealsViewHolder
                if (colorPosition < colors.size - 1) {
                    ++colorPosition
                } else {
                    colorPosition = 0
                }
                holder.binding.context = holder.itemView.context
                holder.binding.color = colors[colorPosition]
                holder.binding.dealData = dealData
                holder.binding.coupon=dealData.coupon
                UtilsFunction.setAnimation(viewHolder.itemView.context, viewHolder.itemView, R.anim.animation_slidefast_in_right)

                holder.binding.cvDetail.setOnClickListener {
                    ShowDetails(holder.itemView.context, MyApp.MySingleton.getAppContext().getString(R.string.deal_detail), dealData.details).show()
                }
                holder.binding.cvTermCon.setOnClickListener {
                    ShowDetails(
                        holder.itemView.context,
                        MyApp.MySingleton.getAppContext()
                            .getString(R.string.deal_s_terms_conditions),
                        dealData.terms_and_conditions
                    ).show()
                }


                holder.binding.cvShowDeal.setOnClickListener {
                    if(MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN))
                    {
                        holder.binding.isProgress=true
                        showDealListener.dealCodeResponse(dealData,object :ShowDealListener{
                            override fun dealCodeResponse(
                                data: Any,
                                callBackListener: ShowDealListener?
                            )
                            {
                                holder.binding.isProgress=false
                                val dealCode = data as DealCode
                                if(dealCode.status)
                                {
                                    //set coupon data in current list
                                    dealData.coupon =dealCode.coupon
                                    tabData!![position]=dealCode

                                    holder.binding.couponCode= dealData.coupon!!.code
                                    holder.binding.clCouponCode.visibility = View.VISIBLE
                                    holder.binding.clDealDetail.visibility = View.GONE
                                    UtilsFunction.setAnimation(holder.itemView.context, holder.binding.clCouponCode, R.anim.animation_bounce)
                                    android.os.Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                        holder.binding.clCouponCode.visibility = View.GONE
                                        holder.binding.clDealDetail.visibility = View.VISIBLE
                                        holder.binding.coupon = dealCode.coupon
                                        UtilsFunction.setAnimation(holder.itemView.context, holder.binding.clDealDetail, R.anim.animation_bounce)

                                    }, 1000)
                                }
                            }
                        })
                    }else
                    {
                        ShowAlert("Login Alert..!!","Do you want to Login?",this,context,R.drawable.login).show()
                    }
                }

            }

            2 -> {
                val galleryData = tabData!![position] as String
                val holder = viewHolder as GalleryViewHolder
                holder.binding.image = galleryData
                holder.itemView.setOnClickListener {
                    ImageZoomer(
                        holder.itemView.context,
                        tabData as ArrayList<String>,
                        position
                    ).show()
                }
                UtilsFunction.setAnimation(
                    viewHolder.itemView.context,
                    viewHolder.itemView,
                    R.anim.animation_fall_down
                )
            }
        }
    }

    public fun submitLayout(tabData: ArrayList<Any>, layoutType: Int) {
        this.tabData = tabData
        this.layoutType = layoutType
        notifyDataSetChanged()
    }

    override fun onClick(clickCode: Int, data: Any?)
    {
           context.startActivity(Intent(context,LoginActivity::class.java))
    }

}
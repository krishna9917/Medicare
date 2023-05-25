package com.application.comeato.Adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.R
import com.application.comeato.databinding.LayoutPlanFeatureItemBinding

class MembershipPlanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var colorPosition = -1
    private class PlanFeatureViewHolder(val binding: LayoutPlanFeatureItemBinding): RecyclerView.ViewHolder(binding.root)

    private var colors= intArrayOf(R.color.color_1,R.color.color_2,R.color.color_3,R.color.color_4,R.color.color_5,R.color.color_6,R.color.color_7)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  PlanFeatureViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_plan_feature_item,parent,false))
    }

    override fun getItemCount(): Int
    {
       return 5
    }

    

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        val holder =viewHolder as PlanFeatureViewHolder
        if(colorPosition<colors.size)
        {
            colorPosition++
        }else
        {
            colorPosition=0
        }
        holder.binding.context=holder.itemView.context
        holder.binding.color=colors[colorPosition]

    }

}
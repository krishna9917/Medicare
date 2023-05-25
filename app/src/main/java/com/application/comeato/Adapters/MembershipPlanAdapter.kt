package com.application.comeato.Adapters
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.PaymentGateway.CCAvenue.CCAvenue
import com.application.comeato.R
import com.application.comeato.databinding.LayoutPlanFeatureItemBinding
import com.application.comeato.models.AnnualPlane
import com.application.comeato.models.SubPlane

class MembershipPlanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var subPlanList:ArrayList<SubPlane>?=null
    private var annualPlane: AnnualPlane= AnnualPlane()

    private class PlanFeatureViewHolder(val binding: LayoutPlanFeatureItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return  PlanFeatureViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_plan_feature_item,parent,false))
    }

    override fun getItemCount(): Int
    {
       return subPlanList?.size?:0
    }

    

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    {
        val holder =viewHolder as PlanFeatureViewHolder
        holder.binding.planData = subPlanList!![position]
        holder.binding.txtName.text = subPlanList!![position].name
        holder.binding.txtValidity.text=subPlanList!![position].valid_months +" "+holder.itemView.context.getString(R.string.months)
        holder.binding.txtPrice.text =holder.itemView.context.getString(R.string.rupee_symbol)+subPlanList!![position].amount
        holder.binding.isMainPlanActive = annualPlane.is_membership_active
        if(subPlanList!![position].user_subscription_status!=1)
        {
            holder.binding.txtBtn.text=holder.itemView.context.getText(R.string.buy_now)
        }else
        {
            holder.binding.txtBtn.text=holder.itemView.context.getText(R.string.active)
        }

        holder.binding.cvBuyPlan.setOnClickListener {
            val goToPaymentGateway = Intent(holder.itemView.context,CCAvenue::class.java)
            goToPaymentGateway.putExtra("MainPlanId",annualPlane.id.toString())
            goToPaymentGateway.putExtra("SubPlanId", subPlanList!![position].id.toString())
            holder.itemView.context.startActivity(goToPaymentGateway)
        }

    }



    public fun submitSubPlanList(subPlanList:ArrayList<SubPlane>, annual_plane: AnnualPlane)
    {
        this.subPlanList=subPlanList
        this.annualPlane =  annual_plane
        notifyDataSetChanged()
    }


}
package com.application.comeato.Activity.MembershipPlan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.application.comeato.Activity.CustomerSupport.SupportActivity
import com.application.comeato.Adapters.MembershipPlanAdapter
import com.application.comeato.PaymentGateway.CCAvenue.CCAvenue
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityMembershipPlanBinding
import com.comeato.Utilities.MyApp
import java.lang.Math.abs


class MembershipPlanActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityMembershipPlanBinding.inflate(layoutInflater)
    }


    private val repository by lazy {
        MembershipRepository(MyApp.MySingleton.getApiInterface(), this)
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MembershipModelViewFactory(repository)
        )[MembershipModelView::class.java]
    }

    private val subPlanAdapter by lazy {
        MembershipPlanAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer() {
        binding.imgBack.setOnClickListener(this)
        binding.btnSupport.setOnClickListener(this)
        binding.cvBuySubscription.setOnClickListener(this)

        binding.vpPlanFeatures.adapter = subPlanAdapter
        binding.vpPlanFeatures.apply {
            setAlpha(true)
            set3DItem(true)
        }


        getData()

    }

    private fun getData() {
        viewModel.subscriptionPlanResponse.observe(this) {
            MyApp.MySingleton.IS_PAYMENT_SUCCESS = false
            if (it.status) {
                binding.subscriptionData = it
                subPlanAdapter.submitSubPlanList(it.sub_plane,it.annual_plane)
            } else
            {
              UtilsFunction.showError(this,it.message)
            }
        }


    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBack ->{
                finish()
                onBackPressedDispatcher.onBackPressed()
            }
            R.id.btnSupport -> {
                startActivity(Intent(this, SupportActivity::class.java))
            }
            R.id.cvBuySubscription -> {
                val goToPaymentGateway = Intent(this,CCAvenue::class.java)
                goToPaymentGateway.putExtra("MainPlanId",binding.subscriptionData?.annual_plane?.id.toString())
                goToPaymentGateway.putExtra("SubPlanId", "")
                startActivity(goToPaymentGateway)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(MyApp.MySingleton.IS_PAYMENT_SUCCESS) repository.getMemberShipPlan()
    }
}
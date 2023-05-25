package com.application.comeato.Activity.MembershipPlan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.application.comeato.Adapters.MembershipPlanAdapter
import com.application.comeato.R
import com.application.comeato.databinding.ActivityMembershipPlanBinding
import java.lang.Math.abs


class MembershipPlanActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityMembershipPlanBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer() {
        binding.imgBack.setOnClickListener(this)
        binding.vpPlanFeatures.adapter = MembershipPlanAdapter()
        setPlanViewPager()
    }

    private fun setPlanViewPager() {

        //product image Slider
        binding.vpPlanFeatures.clipToPadding = false
        binding.vpPlanFeatures.clipChildren = false
        binding.vpPlanFeatures.offscreenPageLimit = 3
        binding.vpPlanFeatures.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(0))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.28f
        }
        binding.vpPlanFeatures.setPageTransformer(transformer)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBack -> finish();
        }

    }
}
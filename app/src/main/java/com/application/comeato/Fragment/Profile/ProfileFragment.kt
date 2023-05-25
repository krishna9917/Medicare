package com.comeato.Fragment.Profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.application.comeato.Activity.CustomerSupport.SupportActivity
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.Activity.MembershipPlan.MembershipPlanActivity
import com.application.comeato.Activity.MyDeals.MyDealsActivity
import com.application.comeato.Activity.Profile.ProfileActivity
import com.application.comeato.Activity.Referral.ReferralActivity
import com.application.comeato.Activity.Transactions.TransactionsActivity
import com.application.comeato.Activity.WishList.WishListActivity
import com.application.comeato.Adapters.MyAccountTabAdapter
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.Interfaces.FragmentLoadCallBack
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.databinding.FragmentProfileBinding
import com.application.comeato.models.MyAccountTabs
import com.application.comeato.models.User
import com.comeato.Utilities.MyApp

class ProfileFragment() : Fragment(), AdapterClickListener, View.OnClickListener {


    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private val myAccountTabAdapter by lazy {
        MyAccountTabAdapter(this)
    }
    private val tabList = ArrayList<MyAccountTabs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializer()
    }

    private fun initializer()
    {
        LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(Intent("accountFragment"))
        binding.rvAccountTabs.adapter = myAccountTabAdapter
        binding.cvEditProfile.setOnClickListener(this)
        binding.cvLogin.setOnClickListener(this)

    }

    private fun setLayoutAsPerLoginStatus()
    {
        tabList.clear()

        if (MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN))
        {
            binding.loginUserData=MyLocalMemory.getObject(MyApp.MySingleton.USER_DETAILS,User())

            binding.isLogin=true
            tabList.add(MyAccountTabs(R.drawable.img_color_profile, "My Profile", "Get profile details and Logout",0))

            tabList.add(MyAccountTabs(R.drawable.img_color_deals, "My Deals", "Get your deals",2))

        }else
        {
            binding.isLogin=false
        }

        tabList.add(MyAccountTabs(R.drawable.ic_liked, "Fav Properties", "Your fav deals",1))
        if(MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN))
        {
            tabList.add(MyAccountTabs(R.drawable.img_color_subscription_plan, "Membership Plan", "Choose your plan and get more discounts"
                ,3 )
            )
            tabList.add(MyAccountTabs(R.drawable.img_color_transaction, "Transactions", null,4))
        }
        tabList.add(MyAccountTabs(R.drawable.img_color_support, "Customer Support", null,5))
        tabList.add(MyAccountTabs(R.drawable.img_color_invite, "Invite your friend", null,6))

        myAccountTabAdapter.submitList(tabList)
    }


    override fun onClick(p0: View?) {
        when (p0?.id)
        {
            R.id.cvEditProfile -> {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra(getString(R.string.type), getString(R.string.edit))
                startActivity(intent)
            }
            R.id.cvLogin->startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int) {
        val tabData = data as MyAccountTabs
        when (tabData.id) {
            0 -> {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra(getString(R.string.type), getString(R.string.show))
                startActivity(intent)
            }
            1-> startActivity(Intent(context,WishListActivity::class.java))
            2-> startActivity(Intent(context,MyDealsActivity::class.java))
            3-> startActivity(Intent(context,MembershipPlanActivity::class.java))
            4->startActivity(Intent(context,TransactionsActivity::class.java))
            5-> startActivity(Intent(context,SupportActivity::class.java))
            6 -> startActivity(Intent(context,ReferralActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        setLayoutAsPerLoginStatus()
    }
}
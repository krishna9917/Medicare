package com.comeato.Fragment.Profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.application.comeato.Activity.MembershipPlan.MembershipPlanActivity
import com.application.comeato.Activity.Profile.ProfileActivity
import com.application.comeato.Adapters.MyAccountTabAdapter
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.FragmentProfileBinding
import com.application.comeato.models.MyAccountTabs
class ProfileFragment : Fragment(), AdapterClickListener, View.OnClickListener {

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
        Initializer()
    }

    private fun Initializer() {
        binding.rvAccountTabs.adapter = myAccountTabAdapter
        binding.cvEditProfile.setOnClickListener(this)
        setLayoutAsPerLoginStatus()
    }

    private fun setLayoutAsPerLoginStatus() {
        tabList.clear()
        if (true) {
            tabList.add(
                MyAccountTabs(
                    R.drawable.ic_profile,
                    "My Profile",
                    "Get profile details and Logout"
                )
            )
            tabList.add(MyAccountTabs(R.drawable.ic_heart, "Fav Deals", "Your fav deals"))
            tabList.add(
                MyAccountTabs(
                    R.drawable.ic_subscription,
                    "Membership Plan",
                    "Choose your plan"
                )
            )
            tabList.add(MyAccountTabs(R.drawable.ic_profile, "Transactions", null))
        }
        tabList.add(MyAccountTabs(R.drawable.ic_support, "Customer Support", null))
        tabList.add(MyAccountTabs(R.drawable.ic_invite, "Invite your friend", null))

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
        }

    }

    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int) {
        when (selectedPosition) {
            0 -> {
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra(getString(R.string.type), getString(R.string.show))
                startActivity(intent)
            }
            2-> startActivity(Intent(context,MembershipPlanActivity::class.java))
        }
    }
}
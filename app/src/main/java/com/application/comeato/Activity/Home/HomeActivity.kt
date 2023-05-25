package com.comeato.Activity.Home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.comeato.R
import com.application.comeato.databinding.ActivityHomeBinding
import com.comeato.Fragment.Home.HomeFragment
import com.comeato.Fragment.Offers.OffersFragment
import com.comeato.Fragment.Profile.ProfileFragment
import com.comeato.Fragment.Property.PropertyFragment
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    companion object {
        private const val ID_HOME = 0
        private const val ID_PROPERTY = 1
        private const val ID_OFFERS = 2
        private const val ID_PROFILE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer() {
        replaceFragment(HomeFragment())
        setBottomNavigationInNormalWay()
    }

    private fun setBottomNavigationInNormalWay() {

        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_home, // the icon
                R.drawable.avd_home, // the AVD that will be shown in FAB
            ),
            CbnMenuItem(
                R.drawable.ic_property ,
                R.drawable.avd_property,
            ),
            CbnMenuItem(
                R.drawable.ic_offer,
                R.drawable.avd_offer
            ),
            CbnMenuItem(
                R.drawable.ic_profile,
                R.drawable.avd_profile
            )
        )

        binding.bottomNavigationView.setMenuItems(menuItems, 0)

        binding.bottomNavigationView.setOnMenuItemClickListener { cbnMenuItem, index ->
            when (index) {
                ID_HOME -> replaceFragment(HomeFragment())
                ID_PROPERTY -> replaceFragment(PropertyFragment())
                ID_OFFERS -> replaceFragment(OffersFragment())
                ID_PROFILE -> replaceFragment(ProfileFragment())
            }
        }

    }

    private fun replaceFragment(replaceFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContainer, replaceFragment)
        fragmentTransaction.commit()
    }


}
package com.comeato.Activity.Home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityHomeBinding
import com.comeato.Fragment.Home.HomeFragment
import com.comeato.Fragment.Offers.OffersFragment
import com.comeato.Fragment.Profile.ProfileFragment
import com.comeato.Fragment.Property.PropertyFragment

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }


    private var pressBack = 0

    private var isHomeFragLoaded = false
    private var isPropertiesFragLoaded = false
    private var isOfferFragLoaded = false
    private var isAccountFragLoaded = false

    private val homeLoadBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            isHomeFragLoaded = true
        }
    }

    private val propertiesLoadBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            isPropertiesFragLoaded = true
        }
    }

    private val offerLoadBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            isOfferFragLoaded = true
        }
    }
    private val accountLoadBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            isAccountFragLoaded = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(homeLoadBroadcastReceiver, IntentFilter("homeFragment"))
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(propertiesLoadBroadcastReceiver, IntentFilter("propertiesFragment"))
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(offerLoadBroadcastReceiver, IntentFilter("offerFragment"))
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(accountLoadBroadcastReceiver, IntentFilter("accountFragment"))
        initializer()
    }

    private fun initializer() {
        binding.fragmentVisibility = 1
        replaceFragment(R.id.flHomeContainer, HomeFragment())

        binding.bottomNavigation.visibility = View.GONE
        when (intent.getIntExtra(getString(R.string.homelayouttype), 0)) {
            1 -> binding.bottomNavigation.selectedItemId = R.id.nav_properties
            2 -> binding.bottomNavigation.selectedItemId = R.id.nav_offers
            else -> {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }


        binding.bottomNavigation?.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.nav_home -> {
                    if (!isHomeFragLoaded)
                        replaceFragment(R.id.flHomeContainer, HomeFragment())
                    binding.fragmentVisibility = 1
                }

                R.id.nav_properties -> {
                    if (!isPropertiesFragLoaded)
                        replaceFragment(R.id.flProductContainer, PropertyFragment())
                    binding.fragmentVisibility = 2
                }

                R.id.nav_offers -> {
                    if (!isOfferFragLoaded)
                        replaceFragment(R.id.flOffersContainer, OffersFragment())
                    binding.fragmentVisibility = 4
                }

                R.id.nav_account -> {
                    if (!isAccountFragLoaded)
                        replaceFragment(R.id.flAccountContainer, ProfileFragment())
                    binding.fragmentVisibility = 5

                }
            }
            return@setOnItemSelectedListener true
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (pressBack == 1) {
                    finish()
                } else {
                    UtilsFunction.showToast(
                        this@HomeActivity,
                        getString(R.string.press_again_to_exit_app)
                    )
                    pressBack++
                }

            }
        })

    }
    private fun replaceFragment(container: Int, replaceFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(container, replaceFragment)
        fragmentTransaction.commit()
    }
}


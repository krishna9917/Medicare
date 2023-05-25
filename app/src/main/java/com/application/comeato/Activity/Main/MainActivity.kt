package com.comeato.Activity.Main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.BuildConfig
import com.application.comeato.Dialog.InternetConnectivity
import com.application.comeato.Interfaces.CurrentLocationListener
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityMainBinding
import com.comeato.Activity.Home.HomeActivity
import com.comeato.Utilities.MyApp


class MainActivity : AppCompatActivity(),CurrentLocationListener,DialogClickListener
{
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var appNameRunnable: Runnable? = null
    private val appNameHandler = Handler(Looper.getMainLooper())

    private var animation: AnimatedVectorDrawable? = null

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
            {
                UtilsFunction.getLastLocation(this,this)
            }else
            {
                logoAnimator()
            }
        }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        if (!UtilsFunction.isInternetConnected(MyApp.MySingleton.getAppContext()))
        {
            InternetConnectivity(this, this).show()

        }else
        {
            onCurrentLocation()
        }
    }

    private fun onCurrentLocation()
    {
        if (UtilsFunction.isLocationPermissionGranted(this))
        {
            UtilsFunction.getLastLocation(this,this)
            logoAnimator()
        } else
        {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun logoAnimator()
    {
        runOnUiThread(Runnable {
            UtilsFunction.setAnimation(this, binding.cvLogo, R.anim.animation_fall_down)
            val d: Drawable = binding.imgAvdDiscount.drawable
            if (d is AnimatedVectorDrawable) { animation = d
                animation?.start()
            }
            var index = 0
            appNameRunnable = Runnable {
                try {
                    index++
                    if (index <= getString(R.string.comeato).length)
                    {
                        binding.txtAppName.text = getString(R.string.comeato).subSequence(0, index)
                        appNameHandler.postDelayed(appNameRunnable!!, 220)
                    } else
                    {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finishAffinity()
                        appNameHandler.removeCallbacksAndMessages(null)
                    }
                }catch (e:Exception)
                {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                    appNameHandler.removeCallbacksAndMessages(null)
                }
            }

            appNameHandler.postDelayed(appNameRunnable!!, 1000)
        })
    }


    override fun onGetCurrentLocation(string: String) {
//        logoAnimator()
    }

    override fun onError(string: String) {
//        logoAnimator()
    }

    override fun onClick(clickCode: Int, data: Any?) {
//        onCurrentLocation()
    }


}
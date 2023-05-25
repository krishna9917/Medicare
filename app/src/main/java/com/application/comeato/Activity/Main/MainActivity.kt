package com.comeato.Activity.Main

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()
{
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var appNameRunnable: Runnable? = null
    private val appNameHandler = Handler(Looper.getMainLooper())

    private var animation: AnimatedVectorDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer();
    }

    private fun Initializer()
    {

    }

    override fun onStart() {
        super.onStart()
        logoAnimator()
    }

    private fun logoAnimator()
    {
        UtilsFunction.setAnimation(this,binding.cvLogo,R.anim.animation_fall_down)

        val d: Drawable = binding.imgAvdDiscount.drawable
        if (d is AnimatedVectorDrawable)
        {
            animation = d
            animation?.start()
        }
        var index=0
        appNameRunnable = Runnable{
            binding.txtAppName.text = getString(R.string.comeato).subSequence(0, index++)
            if (index <= getString(R.string.comeato).length)
            {
                appNameHandler.postDelayed(appNameRunnable!!, 220)
            }else
            {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        appNameHandler.postDelayed(appNameRunnable!!, 1000)
    }

}
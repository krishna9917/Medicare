package com.application.comeato.Activity.Profile
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.comeato.R
import com.application.comeato.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity()
{
    private  val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer()
    {
        binding.editable=intent.getStringExtra(getString(R.string.type))!!.equals(getString(R.string.edit))
    }

    override fun onStart() {
        super.onStart()
    }
}
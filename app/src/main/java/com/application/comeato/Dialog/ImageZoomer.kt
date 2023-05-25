package com.application.comeato.Dialog
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.application.comeato.databinding.LayoutImageZoomerBinding

class ImageZoomer(context: Context) : AlertDialog(context)
{
    private val binding by lazy {
        LayoutImageZoomerBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        show()
    }

}
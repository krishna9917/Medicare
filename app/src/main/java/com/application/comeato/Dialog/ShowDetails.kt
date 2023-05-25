package com.application.comeato.Dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import com.application.comeato.R
import com.application.comeato.databinding.LayoutShowDetailBinding

class ShowDetails(context: Context,val title:String,val data:String) :AlertDialog(context), View.OnClickListener
{

    private val binding by lazy {
        LayoutShowDetailBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.txtTitle.text = title
        binding.txtData.text= HtmlCompat.fromHtml(data,HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.imgBack.setOnClickListener(this)

    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
          R.id.imgBack -> dismiss()
        }
    }

}
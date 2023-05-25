package com.application.comeato.Dialog


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.application.comeato.Adapters.ImageZoomerAdapter
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutImageZoomerBinding
import com.application.comeato.models.ImageData


class ImageZoomer(context: Context, private val imageList:ArrayList<String>, private var clickPosition:Int=0) : AlertDialog(context,R.style.FullScreenDialog), View.OnClickListener,AdapterClickListener
{

    private val binding by lazy {
        LayoutImageZoomerBinding.inflate(layoutInflater)
    }
    private val imageListAdapter by lazy {
        ImageZoomerAdapter(1,imageList,this,clickPosition)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window!!.attributes.windowAnimations=R.style.DialogAnimation
        Initializer()
    }

    private fun Initializer()
    {
        binding.vpImages.adapter = ImageZoomerAdapter(0,imageList,this)
        binding.cvBack.setOnClickListener(this)
        binding.rvImageList.adapter= imageListAdapter
        binding.vpImages.currentItem = clickPosition
        binding.rvImageList.scrollToPosition(clickPosition)
        val imageRvLayout = binding.rvImageList.layoutManager
        binding.vpImages.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int)
            {
                imageListAdapter.setSelectedItem(position)
                if (imageRvLayout is LinearLayoutManager)
                {
                    val linearLayoutManager = imageRvLayout as LinearLayoutManager
                    val firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()
                    val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()
                    if (position < firstVisiblePosition || position > lastVisiblePosition)
                    {
                        binding.rvImageList.smoothScrollToPosition(position)
                    }
                }
            }
        })
    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
            R.id.cvBack->dismiss()
        }
    }

    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int)
    {
        binding.vpImages.setCurrentItem(selectedPosition,true)
    }


}
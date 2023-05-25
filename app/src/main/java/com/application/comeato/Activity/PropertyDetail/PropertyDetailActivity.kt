package com.application.comeato.Activity.PropertyDetail

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.comeato.Activity.CustomerSupport.SupportActivity
import com.application.comeato.Activity.MembershipPlan.MembershipPlanActivity
import com.application.comeato.Activity.WishList.WishListRepository
import com.application.comeato.Activity.WishList.WishListViewModel
import com.application.comeato.Activity.WishList.WishListViewModelFactory
import com.application.comeato.Adapters.PropertyDetailsAdapter
import com.application.comeato.Adapters.PropertyTabDataAdapter
import com.application.comeato.Dialog.ShowAlert
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.Interfaces.ShowDealListener
import com.application.comeato.R
import com.application.comeato.RoomDb.MyDatabase
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityPropertyDetailBinding
import com.application.comeato.models.Deal
import com.application.comeato.models.FeaturedProperty
import com.application.comeato.models.Tab
import com.application.comeato.models.WishListData
import com.comeato.Utilities.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PropertyDetailActivity : AppCompatActivity(), View.OnClickListener, AdapterClickListener,ShowDealListener,DialogClickListener
{

    private val roomDatabase by lazy {
        MyDatabase.getInstance(this).getRoomDao()
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityPropertyDetailBinding>(
            this,
            R.layout.activity_property_detail
        )
    }
    private var propertyTabDataAdapter = PropertyTabDataAdapter(this,this,this)

    private val imageSliderAdapter by lazy {
        PropertyDetailsAdapter(1)
    }
    private val servicesAdapter by lazy {
        PropertyDetailsAdapter(this, 2)
    }
    private val similarPropertyAdapter by lazy {
        PropertyDetailsAdapter(this,3)
    }

    private val repository by lazy {
        PropertyDetailRepository(MyApp.MySingleton.getApiInterface(), this,roomDatabase)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, PropertyDetailViewModelFactory(repository))[PropertyDetailViewModel::class.java]
    }


    private val wishListRepository by lazy {
        WishListRepository(MyApp.MySingleton.getApiInterface(),this,roomDatabase)
    }

    private val wishListViewModel by lazy {
        ViewModelProvider(this,WishListViewModelFactory(wishListRepository))[WishListViewModel::class.java]
    }

    private lateinit var showDealCallBack:ShowDealListener


    private var isWishListed=false

    private lateinit var wishlistProperty:WishListData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Initializer()
    }

    private fun Initializer()
    {
        binding.propertyName=intent.getStringExtra(getString(R.string.propertyName))

        binding.txtReadMoreAndLess.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
        binding.llPhone.setOnClickListener(this)
        binding.llAddress.setOnClickListener(this)
        binding.btnSupport.setOnClickListener(this)
        binding.imgWishList.setOnClickListener(this)



        binding.rvPropertyImages.adapter = imageSliderAdapter
        binding.rvTabs.adapter = servicesAdapter
        binding.rvSimilarOption.adapter = similarPropertyAdapter
        binding.rvTabData.adapter = propertyTabDataAdapter



        intent.getStringExtra(getString(R.string.propertyid))
            ?.let { repository.getPropertyDetail(it) }



        getData()

    }

    private fun getData()
    {
        var propertyInDb:WishListData?=null

        lifecycleScope.launch {
            propertyInDb = wishListRepository.getWishList(intent.getStringExtra(getString(R.string.propertyid))!!)
        }

        viewModel?.propertyDetail?.observe(this) {
            binding.propertyData = it

            if((propertyInDb?.property_id==it.property.id) || it.property.is_in_wishlist)
            {
                isWishListed = true
                binding.isWishlisted=true
            }
            wishlistProperty = WishListData(it.property.featured_image_url,it.property.id,it.property.locality_name,it.property.name)
            observeDescriptionText()
            imageSliderAdapter.submitData(it)
            servicesAdapter.submitData(it)
            similarPropertyAdapter.submitData(it)
            setDataAsPerData(it.property.tabs[0])
        }


        viewModel.dealCodeData.observe(this){
            showDealCallBack.dealCodeResponse(it,null)
            if(!it.status && it.error_code==404)
            {
                ShowAlert("You don't have membership...!!","Do you want to get Membership?",this,this,R.drawable.membership).show()
            }else if(!it.status)
            {
               UtilsFunction.showError(this,it.message)
            }
        }

        wishListViewModel.wishListResponse.observe(this){
            binding.isWishlistProcessing=false
            if(it.status)
            {
                isWishListed = !isWishListed
                binding.isWishlisted = isWishListed
            }else
            {
                UtilsFunction.showError(this,it.message)
            }
        }
    }

    private fun observeDescriptionText()
    {
        binding.txtDescription.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val layout: Layout = binding.txtDescription.getLayout()
                    if (layout != null) {
                        val lastLineIndex = layout.lineCount - 1
                        val ellipsisCount = layout.getEllipsisCount(lastLineIndex)
                        if (ellipsisCount > 0) {
                            binding.txtReadMoreAndLess.visibility = View.VISIBLE
                        } else {
                            binding.txtReadMoreAndLess.visibility = View.GONE
                        }
                    }
                    binding.txtReadMoreAndLess.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
    }

    override  fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtReadMoreAndLess -> {
                if (binding.txtReadMoreAndLess.text.toString() == getString(R.string.read_more)) {
                    binding.isReadMoreVisible = true
                    UtilsFunction.setAnimation(
                        this,
                        binding.txtFullDescription,
                        R.anim.animation_fall_down
                    )
                } else {
                    binding.isReadMoreVisible = false
                }
            }
            R.id.llAddress->
            {
                UtilsFunction.openMapByAddress(binding.txtAddress.text.toString(),this)
            }
            R.id.llPhone ->{
                UtilsFunction.contactDialer(binding.txtMobile.text.toString())
            }
            R.id.imgBack -> {
                finish()
            }
            R.id.btnSupport->{
                startActivity(Intent(this,SupportActivity::class.java))
            }
            R.id.imgWishList->
            {
              binding.isWishlistProcessing = true
                lifecycleScope.launch(Dispatchers.IO) {
                    wishListRepository.wishListOperation(wishlistProperty,!isWishListed)
                }
            }
        }
    }


    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int)
    {
        when(click_layout_code)
        {
            101->{
                val tab = data as Tab
                setDataAsPerData(tab)
            }
            103->{
                val property = data as FeaturedProperty
                val propertyIntent = Intent(this,PropertyDetailActivity::class.java)
                propertyIntent.putExtra(getString(R.string.propertyid),property.id.toString())
                propertyIntent.putExtra(getString(R.string.propertyName),property.name)
                startActivity(propertyIntent)
            }
        }
    }

    private fun setDataAsPerData(tab: Tab) {
        when (tab.layout_type) {
            1 -> {
                   binding.rvTabData.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    propertyTabDataAdapter.submitLayout(tab.data as ArrayList<Any>, tab.layout_type)
            }
            2 -> {
                binding.rvTabData.layoutManager = GridLayoutManager(this, 3)
                propertyTabDataAdapter.submitLayout(tab.data as ArrayList<Any>, tab.layout_type)
            }
        }
    }

    override fun dealCodeResponse(data: Any, callBackListener: ShowDealListener?)
    {
         val dealData = data as Deal
        intent.getStringExtra(getString(R.string.propertyid))?.let { repository.showDealCode(it,
            dealData.id.toString()
        ) }
        showDealCallBack= callBackListener!!
    }

    override fun onClick(clickCode: Int, data: Any?)
    {
        startActivity(Intent(this,MembershipPlanActivity::class.java))
    }


}
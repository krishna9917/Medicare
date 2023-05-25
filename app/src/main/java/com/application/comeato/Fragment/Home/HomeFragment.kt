package com.comeato.Fragment.Home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.application.comeato.Activity.Location.LocationActivity
import com.application.comeato.Activity.PropertyDetail.PropertyDetailActivity
import com.application.comeato.Activity.Search.SearchActivity
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.Interfaces.FragmentLoadCallBack
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.ViewPagerTransformation.CubeOutScalingTransformation
import com.application.comeato.ViewPagerTransformation.FadeOutTransformation
import com.application.comeato.databinding.FragmentHomeBinding
import com.application.comeato.models.*
import com.comeato.Adapters.HomeAdapter
import com.comeato.Utilities.MyApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class HomeFragment() : Fragment(), View.OnClickListener, AdapterClickListener {


    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val topBannerAdapter: HomeAdapter by lazy {
        HomeAdapter(0)
    }
    private val categoryAdapter: HomeAdapter by lazy {
        HomeAdapter(1,this)
    }
    private val brandBannerAdapter: HomeAdapter by lazy {
        HomeAdapter(2)
    }
    private val nearDealsAdapter: HomeAdapter by lazy {
        HomeAdapter(3,this)
    }
    private val topBrandAdapter: HomeAdapter by lazy {
        HomeAdapter(4,this)
    }

    private val specialDealsAdapter: HomeAdapter by lazy {
        HomeAdapter(5,this)
    }

    private val nearLocationAdapter: HomeAdapter by lazy {
        HomeAdapter(6)
    }
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository
    private var searchRunnable: Runnable? = null
    private val searchHandler = Handler(Looper.getMainLooper())
    val dealLayoutManager by lazy {
        GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("AUTH----->", "onViewCreated: "+MyLocalMemory.getString(MyApp.MySingleton.USER_TOKEN))

        binding.txtSearch.setOnClickListener(this)
        homeRepository = HomeRepository(MyApp.MySingleton.getApiInterface(), requireActivity())
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(homeRepository))[HomeViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        Initializer()
    }

    private fun Initializer() {
        binding.llLocation.setOnClickListener(this)
        binding.txtFeaturedProductViewAll.setOnClickListener(this)
        binding.txtTopBrandProductViewAll.setOnClickListener(this)
        binding.txtSpecialDealViewAll.setOnClickListener(this)


        binding.vpTopBanner.adapter = topBannerAdapter
        binding.vpTopBanner.setPageTransformer(CubeOutScalingTransformation())
        binding.rvCategories.adapter = categoryAdapter
        binding.vpBrandBanner.adapter = brandBannerAdapter
        binding.vpBrandBanner.setPageTransformer(FadeOutTransformation())
        binding.rvNearDeals.adapter = nearDealsAdapter
        binding.rvTopBrandProperty.adapter = topBrandAdapter

        binding.rvDayDeal.layoutManager = dealLayoutManager
        binding.rvDayDeal.adapter = specialDealsAdapter
        binding.rvNearLocation.adapter = nearLocationAdapter


        binding.swipeRefresh.setOnRefreshListener {
            homeRepository.getHomeData()
        }


        getData()

    }

    private fun getData() {
        homeViewModel.getHomeData.observe(viewLifecycleOwner) { it ->
            binding.swipeRefresh.isRefreshing=false
            if (it.special_deals.size > 1) {
                dealLayoutManager.spanCount = 2
            }
            topBannerAdapter.submitList(it)

            categoryAdapter.submitList(it)

            brandBannerAdapter.submitList(it)
            binding.dotsIndicator.attachTo(binding.vpBrandBanner)

            nearDealsAdapter.submitList(it)

            topBrandAdapter.submitList(it)

            specialDealsAdapter.submitList(it)

            nearLocationAdapter.submitList(it)
            LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(Intent("homeFragment"))
        }

    }


    private fun startAutoSlider() {
        binding.vpTopBanner.setCurrentItem(0, false)
        runnable = Runnable {
            var currentItem = binding.vpTopBanner.currentItem
            currentItem =
                if (currentItem == binding.vpTopBanner.adapter?.itemCount?.minus(1)) 0 else currentItem + 1
            binding.vpTopBanner.setCurrentItem(currentItem, true)
            handler.postDelayed(runnable!!, 3000)
        }
        handler.postDelayed(runnable!!, 3000)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun searchTypingEffect() {
        var index = 0
        searchRunnable = Runnable {
            if (index == 0) {
                binding.txtSearch.text = ""
            }
            binding.txtSearch.text =
                getString(R.string.search_your_fav_place).subSequence(0, index++)
            if (index <= getString(R.string.search_your_fav_place).length) {
                searchHandler.postDelayed(searchRunnable!!, 100)
            } else {
                searchHandler.postDelayed(searchRunnable!!, 4000)
                index = 0
            }
        }
        searchHandler.postDelayed(searchRunnable!!, 3000)
    }

    override fun onPause() {
        super.onPause()
        binding.txtSearch.text = getString(R.string.search_your_fav_place)
        handler.removeCallbacksAndMessages(null);
        searchHandler.removeCallbacksAndMessages(null);
    }

    override fun onResume() {
        super.onResume()
        val city = MyLocalMemory.getString(MyApp.MySingleton.CURRENT_CITY)
        if (!city.equals(""))
        {
            val location= MyLocalMemory.getString(MyApp.MySingleton.CURRENT_CITY) + ", " + MyLocalMemory.getString(MyApp.MySingleton.CURRENT_STATE)

            if(!location.equals(binding.txtLocation.text.toString(),true) && !binding.txtLocation.text.toString().equals(""))
            {
                homeRepository.getHomeData()
            }
            binding.currentLocation = location
        } else
        {
            binding.currentLocation = getString(R.string.select_your_location)
        }
        searchTypingEffect()
        startAutoSlider()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.llLocation -> {
                startActivity(Intent(context, LocationActivity::class.java))
            }
            R.id.txtFeaturedProductViewAll,
            R.id.txtTopBrandProductViewAll,
            R.id.txtSpecialDealViewAll,
            R.id.txtSearch -> {
                val searchActivity =Intent(context, SearchActivity::class.java)
                if(p0?.id== R.id.txtFeaturedProductViewAll)
                {
                     searchActivity.putExtra(getString(R.string.searchtype),"featured")
                }else if(p0?.id== R.id.txtTopBrandProductViewAll)
                {
                    searchActivity.putExtra(getString(R.string.searchtype),"latest_properties")
                }else if(p0?.id==  R.id.txtSpecialDealViewAll)
                {
                    searchActivity.putExtra(getString(R.string.searchtype),"special_deals")
                }
                startActivity(searchActivity)
            }


        }
    }


    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int)
    {
        when(click_layout_code)
        {
            3,4,5->{
                val property = data as FeaturedProperty
                val propertyIntent = Intent(context,PropertyDetailActivity::class.java)
                propertyIntent.putExtra(getString(R.string.propertyid),property.id.toString())
                propertyIntent.putExtra(getString(R.string.propertyName),property.name)
                startActivity(propertyIntent)
            }
            1->{
                val category = data as Category
                val searchActivity =Intent(context, SearchActivity::class.java)
                searchActivity.putExtra(getString(R.string.searchtype),"category")
                searchActivity.putExtra(getString(R.string.searchcategoryid),category.id.toString())
                startActivity(searchActivity)

            }

        }

    }




}

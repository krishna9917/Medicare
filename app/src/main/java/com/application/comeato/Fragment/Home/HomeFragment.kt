package com.comeato.Fragment.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.application.comeato.Activity.PropertyDetail.PropertyDetailActivity
import com.application.comeato.Activity.Search.SearchActivity
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.ViewPagerTransformation.CubeOutScalingTransformation
import com.application.comeato.ViewPagerTransformation.FadeOutTransformation
import com.application.comeato.databinding.FragmentHomeBinding
import com.application.comeato.models.*
import com.comeato.Adapters.HomeAdapter


class HomeFragment : Fragment(),View.OnClickListener,AdapterClickListener
{
    private val binding: FragmentHomeBinding by lazy{
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val topBannerAdapter:HomeAdapter by lazy {
        HomeAdapter()
    }
    private val categoryAdapter:HomeAdapter by lazy {
        HomeAdapter()
    }
    private val brandBannerAdapter:HomeAdapter by lazy {
        HomeAdapter()
    }
    private val nearDealsAdapter:HomeAdapter by lazy {
        HomeAdapter(this)
    }
    private val topBrandAdapter:HomeAdapter by lazy {
        HomeAdapter(this)
    }

    private val specialDealsAdapter:HomeAdapter by lazy {
        HomeAdapter(this)
    }

    private val nearLocationAdapter:HomeAdapter by lazy {
        HomeAdapter()
    }

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private  lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRepository: HomeRepository
    private var searchRunnable: Runnable? = null
    private val searchHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSearch.setOnClickListener(this)
        Initializer()
    }

    private fun Initializer()
    {
        val topBanner = ArrayList<ImageData>()
        topBanner.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/y/t/p60213-16645391636336da1b89ad3.jpg?tr=tr:n-medium"))
        topBanner.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/q/h/p63848-1665206214634107c6c331c.jpg?w=400"))
        topBanner.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/9/s/o/p9884-16621888756312fd4b0c44a.jpg?w=400"))
        topBanner.add(ImageData("https://d4t7t8y8xqo0t.cloudfront.net/resized/750X436/eazytrendz%2F2744%2Ftrend20200306032517.jpg"))
        topBanner.add(ImageData("https://images.squarespace-cdn.com/content/v1/550482f9e4b02d883729e175/1589206543947-JHNJ3CEUTE23U5AQDWK1/top+restaurants+near+me+tricky+fish+fort+worth+richardson+texas"))



        val categories=ArrayList<Category>()
        categories.add(Category("Veg","0","https://freesvg.org/img/1531813273.png"))
        categories.add(Category("Non-Veg","1","https://thumbs.dreamstime.com/b/food-icon-meal-230247731.jpg"))
        categories.add(Category("Salon","1","https://cdn.w600.comps.canstockphoto.com/hair-salon-logo-with-man-and-scissors-eps-vector_csp80169146.jpg"))
        categories.add(Category("Spa","1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMwe2KP1cnjSGQ4oKGwZYcIU0qJD2Ufv-ryg&usqp=CAU"))
        categories.add(Category("Drinks","1","https://freevector-images.s3.amazonaws.com/uploads/vector/preview/30804/free-vector-bar-logo.jpg"))


        val brandBanner = ArrayList<ImageData>()
        brandBanner.add(ImageData("https://cdn.dribbble.com/users/4815546/screenshots/14333828/djladydzyre.jpg"))
        brandBanner.add(ImageData("https://cdn.dribbble.com/users/2702867/screenshots/8681104/media/3ccd15c431b61aebf30c801e0f7e1db2.jpg?compress=1&resize=400x300&vertical=top"))
        brandBanner.add(ImageData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3c_UDaq1BPtS1bYXvJPGQTrSFyP2enmj5XsQtC6jZkbE7Bvhe7L7tZGCtNjM5z2S5p0Q&usqp=CAU"))
        brandBanner.add(ImageData("https://www.freelogodesign.org/assets/img/bar-logo/desktop-logos-pc.jpg"))
        brandBanner.add(ImageData("https://media-cdn.tripadvisor.com/media/photo-s/15/89/97/6a/our-brand-name-and-logo.jpg"));



        val nearDeals=ArrayList<Property>()
        nearDeals.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpsSvXKhuz2WvdB7sH0O8mc9gcVAIrR_Lu4BjtLcsYfWnv0sc95FYkKfxbNjim2znZCjk&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        nearDeals.add(Property("1","https://img.traveltriangle.com/blog/wp-content/uploads/2017/10/restaurants-in-jaipur-cover1.jpg","Shades Banipark","Banipark,MI Road"))
        nearDeals.add(Property("1","https://media.cnn.com/api/v1/images/stellar/prod/190710135245-12-waterfront-restaurants.jpg?q=w_3498,h_2296,x_0,y_0,c_fill/w_1280","Shades Banipark","Banipark,MI Road"))
        nearDeals.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_02NnJAEgi3jsp5l1SafV6btWCFbQNSMLqGoh8gTzdQgi42ZtCZWN6uV52EQnQ3jT0hI&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        nearDeals.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdaBFq0lMILnF9YJ7jrNIZZTQvxz7W4FM-HtArztIdK86HSSQ8er2gJ4cwN9MME7Un5J0&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        nearDeals.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoyKDLs8DA-WfZBdUUGMoowYhWRwri3y55W5cXlnCKFEG6OfBHV86Y3oNlcBcKiTlAw2A&usqp=CAU","Shades Banipark","Banipark,MI Road"))


        val nearLocations = ArrayList<NearLocationData>()
        nearLocations.add(NearLocationData("Vidhyadhar nagar","1"))
        nearLocations.add(NearLocationData("Vaishali nagar","1"))
        nearLocations.add(NearLocationData("C-Scheme","1"))
        nearLocations.add(NearLocationData("Jawahar Nagar","1"))
        nearLocations.add(NearLocationData("Tonk Road","1"))



        val homeData=HomeData(topBanner,categories,brandBanner,nearDeals,nearDeals,nearDeals,nearLocations)

        binding.vpTopBanner.adapter = topBannerAdapter
        topBannerAdapter.submitList(homeData,0)
        binding.vpTopBanner.setPageTransformer(CubeOutScalingTransformation())


        binding.rvCategories.adapter=categoryAdapter
        categoryAdapter.submitList(homeData,1)

        binding.vpBrandBanner.adapter = brandBannerAdapter
        binding.vpBrandBanner.setPageTransformer(FadeOutTransformation())
        brandBannerAdapter.submitList(homeData,2)
        binding.dotsIndicator.attachTo(binding.vpBrandBanner)

        binding.rvNearDeals.adapter=nearDealsAdapter
        nearDealsAdapter.submitList(homeData,3)

        binding.rvTopBrandProperty.adapter=topBrandAdapter
        topBrandAdapter.submitList(homeData,4)

        binding.rvDayDeal.adapter=specialDealsAdapter
        specialDealsAdapter.submitList(homeData,5)


        binding.rvNearLocation.adapter=nearLocationAdapter
        nearLocationAdapter.submitList(homeData,6)





//        homeRepository= HomeRepository(MyApp.getApiInterface())
//        homeViewModel = ViewModelProvider(this,HomeViewModelFactory(homeRepository))[HomeViewModel::class.java]
//        homeViewModel.getHomeData.observe(viewLifecycleOwner,{it->
//
//        })


    }



    private fun startAutoSlider()
    {
       binding.vpTopBanner.setCurrentItem(0, false)
        runnable = Runnable{
            var currentItem =   binding.vpTopBanner.currentItem
            currentItem = if (currentItem ==   binding.vpTopBanner.adapter?.itemCount?.minus(1)) 0 else currentItem + 1
            binding.vpTopBanner.setCurrentItem(currentItem, true)
            handler.postDelayed(runnable!!, 3000)
        }
        handler.postDelayed(runnable!!, 3000)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun searchTypingEffect()
    {
        var index=0
        searchRunnable = Runnable{
          if(index==0)
          {
              binding.txtSearch.text=""
          }
          binding.txtSearch.text = getString(R.string.search_your_fav_place).subSequence(0, index++)
            if (index <= getString(R.string.search_your_fav_place).length)
            {
                searchHandler.postDelayed(searchRunnable!!, 100)
            }else
            {
                searchHandler.postDelayed(searchRunnable!!, 4000)
                index=0
            }
        }
        searchHandler.postDelayed(searchRunnable!!, 3000)
    }

    override fun onPause()
    {
        super.onPause()
        binding.txtSearch.text=getString(R.string.search_your_fav_place)
        handler.removeCallbacksAndMessages(null);
        searchHandler.removeCallbacksAndMessages(null);
    }

    override fun onResume()
    {
        super.onResume()
        searchTypingEffect()
        startAutoSlider()
    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
            R.id.txtSearch-> startActivity(Intent(context,SearchActivity::class.java))
        }
    }


    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int) {
        startActivity(Intent(context,PropertyDetailActivity::class.java))
    }
}
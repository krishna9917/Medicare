package com.comeato.Fragment.Property

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Activity.PropertyDetail.PropertyDetailActivity
import com.application.comeato.Activity.Search.SearchRepository
import com.application.comeato.Activity.Search.SearchViewModel
import com.application.comeato.Activity.Search.SearchViewModelFactory
import com.application.comeato.Adapters.PropertyCategoryAdapter
import com.application.comeato.Adapters.PropertyAdapter
import com.application.comeato.Fragment.Property.PropertyRepository
import com.application.comeato.Fragment.Property.PropertyViewModel
import com.application.comeato.Fragment.Property.PropertyViewModelFactory
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.Interfaces.FragmentLoadCallBack
import com.application.comeato.R
import com.application.comeato.databinding.FragmentPropertyBinding
import com.application.comeato.models.Category
import com.application.comeato.models.FeaturedProperty
import com.comeato.Utilities.MyApp

class PropertyFragment(val isBackBtn:Boolean=false) : Fragment(),AdapterClickListener,View.OnClickListener
{

    private val binding by lazy {
        FragmentPropertyBinding.inflate(layoutInflater)
    }
    private val propertyAdapter: PropertyAdapter by lazy {
        PropertyAdapter(this)
    }
    private val propertyCategories by lazy {
        PropertyCategoryAdapter(this)
    }

    private val repository by lazy {
        PropertyRepository(MyApp.MySingleton.getApiInterface(),requireContext())
    }
    private val viewModel by lazy {
        ViewModelProvider(this,PropertyViewModelFactory(repository))[PropertyViewModel::class.java]
    }

    // using for properties
    private val searchRepository by lazy {
        SearchRepository(MyApp.MySingleton.getApiInterface(),requireContext())
    }
    private val searchViewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory(searchRepository))[SearchViewModel::class.java]
    }

    private var selected_cat_id=0
    private var page=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Initializer()
        return binding.root
    }

    private fun Initializer()
    {
        binding.ilTitleBar.txtTitle.text=context?.getString(R.string.properties)
        if(isBackBtn)
        {
            binding.ilTitleBar.imgBack.visibility=View.VISIBLE
            binding.ilTitleBar.imgBack.setOnClickListener(this)
        }

        // empty property layout
         binding.llNoItemFound.img.setImageDrawable(context?.getDrawable(R.drawable.ic_property))
         binding.llNoItemFound.txtItem1.text=getString(R.string.sorry_no_property_found)
        binding.llNoItemFound.txtItem2.text="We couldn't find properties related to selected category please select another one"


        binding.rvPropertyCategory.adapter=propertyCategories
        binding.rvProperties.adapter=propertyAdapter


        getData()

    }

    private fun getData()
    {

        viewModel.categories.observe(viewLifecycleOwner){
            propertyCategories.submitList(it.categories)
        }

        searchViewModel.getSearchData.observe(viewLifecycleOwner) { it ->

            binding.isPageProgress=false

            if(it.status)
            {
                LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(Intent("propertiesFragment"))
                page = it.properties.current_page
                if(it.properties.current_page > 1)   propertyAdapter.addList(it.properties.data!!)
                else  propertyAdapter.submitList(it.properties.data!!)
            }else
            {
                page = it.properties.last_page
                if(it.properties.current_page== 1)  propertyAdapter.submitList(it.properties.data!!)
            }


            if(0 < it.properties.data?.size!! || it.status)
            {
                binding.llNoItemFound.clLayout.visibility=View.GONE

            }else if(it.properties.current_page==1 && 0 == it.properties.data?.size!!)
            {
                binding.llNoItemFound.clLayout.visibility=View.VISIBLE
            }

        }


        val paginationScroll = object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager!!.findLastVisibleItemPosition()
                if(lastVisibleItemPosition==propertyAdapter.itemCount-1 && dy > 0 )
                {
                    binding.isPageProgress=true
                    getProperties(true, showProgress = false)
                }
            }
        }
        binding.rvProperties.addOnScrollListener(paginationScroll)

    }


    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int) {
       when(click_layout_code)
       {
           101->{
               val category= data as Category
               selected_cat_id=category.id
               getProperties(showProgress = true)
           }
           102->
           {
               val property = data as FeaturedProperty
               val propertyIntent = Intent(context, PropertyDetailActivity::class.java)
               propertyIntent.putExtra(getString(R.string.propertyid),property.id.toString())
               propertyIntent.putExtra(getString(R.string.propertyName),property.name)
               startActivity(propertyIntent)
           }

       }

    }

    private fun getProperties(requireNextPage:Boolean=false,showProgress:Boolean=false)
    {
        searchRepository.getSearchData("","category",selected_cat_id.toString(),requireNextPage,page,showProgress)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.imgBack -> activity?.onBackPressedDispatcher!!.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
package com.application.comeato.Activity.Search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.comeato.Activity.PropertyDetail.PropertyDetailActivity
import com.application.comeato.Adapters.PropertyAdapter
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.databinding.ActivitySearchBinding
import com.application.comeato.models.FeaturedProperty
import com.application.comeato.models.Property
import com.comeato.Utilities.MyApp

class SearchActivity : AppCompatActivity(), View.OnClickListener ,AdapterClickListener
{
    private val binding by lazy {
           ActivitySearchBinding.inflate(layoutInflater)
    }
    private val searchRepository by lazy {
        SearchRepository(MyApp.MySingleton.getApiInterface(),this)
    }
    private val searchViewModel by lazy {
        ViewModelProvider(this,SearchViewModelFactory(searchRepository))[SearchViewModel::class.java]
    }
    private val searchAdapter: PropertyAdapter by lazy {
        PropertyAdapter(this)
    }
    private val searchHandler=Handler(Looper.myLooper()!!)

    private val searchType by lazy {
         intent?.getStringExtra(getString(R.string.searchtype))?:""
    }

    private val categoryId by lazy {
        intent?.getStringExtra(getString(R.string.searchcategoryid))?:""
    }

    private val searchRunnable by lazy {
        Runnable {
            binding.isSearchProgress=true
            searchRepository.getSearchData(binding.edtSearch.text.toString(),searchType,categoryId)
        }
    }

    private var page=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }


    private fun Initializer()
    {
        binding.edtSearch.isFocusableInTouchMode = true
        binding.edtSearch.requestFocus()
        binding.imgBack.setOnClickListener(this)
        binding.rvSearchProperty.adapter=searchAdapter


        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed(searchRunnable,500)


        searchViewModel.getSearchData.observe(this) { it ->
            binding.isSearchProgress=false
            binding.isPageProgress=false
            if(it.status)
            {
                page = it.properties.current_page
                if(it.properties.current_page > 1)   searchAdapter.addList(it.properties.data!!)
                else  searchAdapter.submitList(it.properties.data!!)
            }else
            {
                page = it.properties.last_page
                if(it.properties.current_page== 1)   searchAdapter.submitList(it.properties.data!!)
            }

           if(page == 1 && (0 == it.properties.data?.size!! || it.properties.data == null) && searchAdapter.itemCount==0)
            {
                binding.llNoItemFound.clLayout.visibility=View.VISIBLE
            }else
            {
                binding.llNoItemFound.clLayout.visibility=View.GONE
            }
        }




        val paginationScroll = object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager!!.findLastVisibleItemPosition()
                if(lastVisibleItemPosition==searchAdapter.itemCount-1 && dy > 0 )
                {
                    binding.isPageProgress=true
                    searchRepository.getSearchData(binding.edtSearch.text.toString(),searchType,categoryId,true,page)
                }
            }
        }
        binding.rvSearchProperty.addOnScrollListener(paginationScroll)






        binding.edtSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            {
                searchHandler.removeCallbacksAndMessages(null)
                searchHandler.postDelayed(searchRunnable,500)
            }
            override fun afterTextChanged(p0: Editable?)
            {}
        })


    }

    override fun onClick(p0: View?)
    {
       when(p0?.id)
       {
           R.id.imgBack-> finish()
       }
    }

    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int)
    {
        val property = data as FeaturedProperty
        val propertyIntent = Intent(this, PropertyDetailActivity::class.java)
        propertyIntent.putExtra(getString(R.string.propertyid),property.id.toString())
        propertyIntent.putExtra(getString(R.string.propertyName),property.name)
        startActivity(propertyIntent)
    }
}
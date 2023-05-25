package com.application.comeato.Activity.Search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.application.comeato.Adapters.PropertyAdapter
import com.application.comeato.R
import com.application.comeato.databinding.ActivitySearchBinding
import com.application.comeato.models.Property
import com.comeato.Utilities.MyApp

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
           ActivitySearchBinding.inflate(layoutInflater)
    }
    private val searchRepository by lazy {
        SearchRepository(MyApp.MySingleton.getApiInterface())
    }
    private val searchViewModel by lazy {
        ViewModelProvider(this,SearchViewModelFactory(searchRepository))[SearchViewModel::class.java]
    }
    private val searchAdapter: PropertyAdapter by lazy {
        PropertyAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer();
    }


    private fun Initializer()
    {
        binding.imgBack.setOnClickListener(this)
        binding.rvSearchProperty.adapter=searchAdapter
//        searchViewModel.getSearchData.observe(this) { it ->
//            searchAdapter.submitList(it)
//        }


        val properties=ArrayList<Property>()
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpsSvXKhuz2WvdB7sH0O8mc9gcVAIrR_Lu4BjtLcsYfWnv0sc95FYkKfxbNjim2znZCjk&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://img.traveltriangle.com/blog/wp-content/uploads/2017/10/restaurants-in-jaipur-cover1.jpg","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://media.cnn.com/api/v1/images/stellar/prod/190710135245-12-waterfront-restaurants.jpg?q=w_3498,h_2296,x_0,y_0,c_fill/w_1280","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_02NnJAEgi3jsp5l1SafV6btWCFbQNSMLqGoh8gTzdQgi42ZtCZWN6uV52EQnQ3jT0hI&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdaBFq0lMILnF9YJ7jrNIZZTQvxz7W4FM-HtArztIdK86HSSQ8er2gJ4cwN9MME7Un5J0&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoyKDLs8DA-WfZBdUUGMoowYhWRwri3y55W5cXlnCKFEG6OfBHV86Y3oNlcBcKiTlAw2A&usqp=CAU","Shades Banipark","Banipark,MI Road"))

        searchAdapter.submitList(properties)


    }

    override fun onClick(p0: View?)
    {
       when(p0?.id)
       {
           R.id.imgBack-> finish()
       }
    }
}
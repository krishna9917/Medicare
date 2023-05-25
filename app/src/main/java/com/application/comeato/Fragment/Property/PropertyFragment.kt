package com.comeato.Fragment.Property

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.comeato.Adapters.PropertyCategoryAdapter
import com.application.comeato.Adapters.PropertyAdapter
import com.application.comeato.databinding.FragmentPropertyBinding
import com.application.comeato.models.Property

class PropertyFragment : Fragment()
{

    private val binding by lazy {
        FragmentPropertyBinding.inflate(layoutInflater)
    }
    private val propertyAdapter: PropertyAdapter by lazy {
        PropertyAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Initializer()
        return binding.root
    }

    private fun Initializer()
    {
        setTitle()
        binding.rvPropertyCategory.adapter=PropertyCategoryAdapter()
        binding.rvProperties.adapter=propertyAdapter

        val properties=ArrayList<Property>()
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpsSvXKhuz2WvdB7sH0O8mc9gcVAIrR_Lu4BjtLcsYfWnv0sc95FYkKfxbNjim2znZCjk&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://img.traveltriangle.com/blog/wp-content/uploads/2017/10/restaurants-in-jaipur-cover1.jpg","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://media.cnn.com/api/v1/images/stellar/prod/190710135245-12-waterfront-restaurants.jpg?q=w_3498,h_2296,x_0,y_0,c_fill/w_1280","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_02NnJAEgi3jsp5l1SafV6btWCFbQNSMLqGoh8gTzdQgi42ZtCZWN6uV52EQnQ3jT0hI&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdaBFq0lMILnF9YJ7jrNIZZTQvxz7W4FM-HtArztIdK86HSSQ8er2gJ4cwN9MME7Un5J0&usqp=CAU","Shades Banipark","Banipark,MI Road"))
        properties.add(Property("1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoyKDLs8DA-WfZBdUUGMoowYhWRwri3y55W5cXlnCKFEG6OfBHV86Y3oNlcBcKiTlAw2A&usqp=CAU","Shades Banipark","Banipark,MI Road"))

        propertyAdapter.submitList(properties)
    }

    private fun setTitle()
    {
        binding.ilTitleBar.txtTitle.text="Properties"
    }
}
package com.application.comeato.Activity.PropertyDetail

import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.comeato.Adapters.PropertyDetailsAdapter
import com.application.comeato.Adapters.PropertyTabDataAdapter
import com.application.comeato.Interfaces.AdapterClickListener
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityPropertyDetailBinding
import com.application.comeato.models.ImageData
import com.application.comeato.models.Property
import com.application.comeato.models.PropertyData
import com.application.comeato.models.PropertyServiceData
import com.application.comeato.models.PropertyServices


class PropertyDetailActivity : AppCompatActivity(), View.OnClickListener, AdapterClickListener
{
    private val binding by lazy {
        ActivityPropertyDetailBinding.inflate(layoutInflater)
    }
    private var propertyTabDataAdapter = PropertyTabDataAdapter(this)

    private val imageSliderAdapter by lazy {
        PropertyDetailsAdapter(1)
    }
    private val servicesAdapter by lazy {
        PropertyDetailsAdapter(this, 2)
    }
    private val similarPropertyAdapter by lazy {
        PropertyDetailsAdapter(3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer() {

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

        binding.txtReadMoreAndLess.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)

        binding.rvPropertyImages.adapter = imageSliderAdapter
        binding.rvTabs.adapter = servicesAdapter
        binding.rvSimilarOption.adapter = similarPropertyAdapter

        val sliderImages = ArrayList<ImageData>()
        sliderImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/y/t/p60213-16645391636336da1b89ad3.jpg?tr=tr:n-medium"))
        sliderImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/q/h/p63848-1665206214634107c6c331c.jpg?w=400"))
        sliderImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/9/s/o/p9884-16621888756312fd4b0c44a.jpg?w=400"))
        sliderImages.add(ImageData("https://d4t7t8y8xqo0t.cloudfront.net/resized/750X436/eazytrendz%2F2744%2Ftrend20200306032517.jpg"))
        sliderImages.add(ImageData("https://images.squarespace-cdn.com/content/v1/550482f9e4b02d883729e175/1589206543947-JHNJ3CEUTE23U5AQDWK1/top+restaurants+near+me+tricky+fish+fort+worth+richardson+texas"))


        val similarProperty = ArrayList<Property>()
        similarProperty.add(
            Property(
                "1",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpsSvXKhuz2WvdB7sH0O8mc9gcVAIrR_Lu4BjtLcsYfWnv0sc95FYkKfxbNjim2znZCjk&usqp=CAU",
                "Shades Banipark",
                "Banipark,MI Road"
            )
        )
        similarProperty.add(
            Property(
                "1",
                "https://img.traveltriangle.com/blog/wp-content/uploads/2017/10/restaurants-in-jaipur-cover1.jpg",
                "Shades Banipark",
                "Banipark,MI Road"
            )
        )
        similarProperty.add(
            Property(
                "1",
                "https://media.cnn.com/api/v1/images/stellar/prod/190710135245-12-waterfront-restaurants.jpg?q=w_3498,h_2296,x_0,y_0,c_fill/w_1280",
                "Shades Banipark",
                "Banipark,MI Road"
            )
        )
        similarProperty.add(
            Property(
                "1",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_02NnJAEgi3jsp5l1SafV6btWCFbQNSMLqGoh8gTzdQgi42ZtCZWN6uV52EQnQ3jT0hI&usqp=CAU",
                "Shades Banipark",
                "Banipark,MI Road"
            )
        )


        val services = ArrayList<PropertyServices>()
        services.add(PropertyServices("All Deals", PropertyServiceData()))
        services.add(PropertyServices("Menu", PropertyServiceData()))
        services.add(PropertyServices("Gallery", PropertyServiceData()))


        val propertyData = PropertyData(sliderImages, services, similarProperty)
        imageSliderAdapter.submitData(propertyData)
        servicesAdapter.submitData(propertyData)
        similarPropertyAdapter.submitData(propertyData)
        binding.rvTabData.adapter = propertyTabDataAdapter
        propertyTabDataAdapter.submitLayout( 0)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtReadMoreAndLess -> {
                if (binding.txtReadMoreAndLess.text.toString() == getString(R.string.read_more)) {
                    binding.isReadMoreVisibile = true
                    UtilsFunction.setAnimation(
                        this,
                        binding.txtFullDescription,
                        R.anim.animation_fall_down
                    )
                } else {
                    binding.isReadMoreVisibile = false
                }

            }

            R.id.imgBack -> {
                finish()
            }
        }
    }


    override fun onClick(data: Any, selectedPosition: Int, click_layout_code: Int)
    {
        if(click_layout_code==101)
        {
            when (selectedPosition) {
                0 -> {
                    binding.rvTabData.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                    propertyTabDataAdapter.submitLayout( 0)
                }
                2 -> {
                    val galleryImages = ArrayList<ImageData>()
                    galleryImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/y/t/p60213-16645391636336da1b89ad3.jpg?tr=tr:n-medium"))
                    galleryImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/6/q/h/p63848-1665206214634107c6c331c.jpg?w=400"))
                    galleryImages.add(ImageData("https://im1.dineout.co.in/images/uploads/restaurant/sharpen/9/s/o/p9884-16621888756312fd4b0c44a.jpg?w=400"))
                    galleryImages.add(ImageData("https://d4t7t8y8xqo0t.cloudfront.net/resized/750X436/eazytrendz%2F2744%2Ftrend20200306032517.jpg"))
                    galleryImages.add(ImageData("https://images.squarespace-cdn.com/content/v1/550482f9e4b02d883729e175/1589206543947-JHNJ3CEUTE23U5AQDWK1/top+restaurants+near+me+tricky+fish+fort+worth+richardson+texas"))
                    binding.rvTabData.layoutManager=GridLayoutManager(this,3)
                    propertyTabDataAdapter.submitLayout( 1,galleryImages)
                }

            }
        }else if(click_layout_code==102)
        {

        }

    }
}
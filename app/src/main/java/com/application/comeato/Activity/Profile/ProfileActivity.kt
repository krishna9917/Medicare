package com.application.comeato.Activity.Profile
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.application.comeato.Dialog.BottomSheet.ImagePickerDialog
import com.application.comeato.Dialog.ShowAlert
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityProfileBinding
import com.application.comeato.models.User
import com.comeato.Utilities.MyApp
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class ProfileActivity() : AppCompatActivity(), View.OnClickListener,DialogClickListener
{

    private  val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        ProfileRepository(MyApp.MySingleton.getApiInterface(),this)
    }

    private  val viewModel by lazy {
        ViewModelProvider(this,ProfileViewModelFactory(repository))[ProfileViewModel::class.java]
    }
    private var profileImage: MultipartBody.Part? = null

    private var userDetail:User?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun Initializer()
    {
        binding.editable=intent.getStringExtra(getString(R.string.type))!!.equals(getString(R.string.edit))
        binding.imgBack.setOnClickListener(this)
        binding.cvCamera.setOnClickListener(this)
        binding.txtLogoutBtn.setOnClickListener(this)

        binding.imgUpdateProfile.setOnClickListener(this)


        viewModel.userProfileData.observe(this){
            if(it.status)
            {
                userDetail=it.user
                MyLocalMemory.putObject(MyApp.MySingleton.USER_DETAILS, it?.user)
                binding.userProfile=it.user
            }
        }

       viewModel.profileUpdateResponse.observe(this){
           binding.isProgress=false
           if(it.status)
           {
               profileImage=null
               MyLocalMemory.putObject(MyApp.MySingleton.USER_DETAILS, it?.user)
               UtilsFunction.showToast(this,it.message)
           }else
           {
               UtilsFunction.showError(this,it.message)
           }
       }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
          R.id.imgBack-> onBackPressedDispatcher.onBackPressed()
          R.id.cvCamera -> ImagePickerDialog(this).show(supportFragmentManager,"ImagePicker")
          R.id.txtLogoutBtn -> ShowAlert("Confirm Exit..!!","Are you sure, You want to exit!",this,this,R.drawable.logout).show()
            R.id.imgUpdateProfile->  {
                if(profileImage==null && userDetail?.name!! == binding.edtName.text.toString())
                {
                    UtilsFunction.showError(this,"No Change in profile")
                }else
                {
                    binding.isProgress=true
                    if(profileImage==null)
                    {
                        profileImage=createFormData("profile_pic", "")
                    }

                    profileImage?.let {
                        repository.updateProfile( binding.edtName.text.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                            it
                        )
                    }
                }
            }
        }
    }
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK)
            {
                val fileUri = data?.data!!
                binding.imgProfile.setImageURI(fileUri)
                val imageFile = File(fileUri.path.toString())
                profileImage = createFormData("profile_pic", imageFile.name,imageFile.asRequestBody("image/*".toMediaTypeOrNull()))
            } else if (resultCode == ImagePicker.RESULT_ERROR)
            {
                UtilsFunction.showToast(this,ImagePicker.getError(data))
            }
        }



    override fun onClick(clickCode: Int, data: Any?) {
        if(clickCode==101)
        {
            ImagePicker.with(this)
                .saveDir(getExternalFilesDir(null)!!)
                .cameraOnly()
                .crop(1f,1f)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }else if(clickCode==102)
        {
            ImagePicker.with(this)
                .saveDir(getExternalFilesDir(null)!!)
                .galleryOnly()
                .crop()
                .compress(1024)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }else if(clickCode==1022)
        {
            MyLocalMemory.putBoolean(MyApp.MySingleton.IS_LOGIN,false)
            MyLocalMemory.putString(MyApp.MySingleton.USER_TOKEN,"")
            onBackPressedDispatcher.onBackPressed()
        }
    }

}
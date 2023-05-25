package com.application.comeato.Dialog.BottomSheet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.R
import com.application.comeato.databinding.LayoutImagePickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ImagePickerDialog(val clickListener:DialogClickListener) : BottomSheetDialogFragment(), View.OnClickListener {

    private val binding by lazy {
        LayoutImagePickerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imgBack.setOnClickListener(this)
        binding.cvCamera.setOnClickListener(this)
        binding.cvGallery.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBack -> dismiss()
            R.id.cvCamera ->
            {
                dismiss()
                clickListener.onClick(101)
            }
           R.id.cvGallery->{
               dismiss()
               clickListener.onClick(102)
           }
        }
    }


}



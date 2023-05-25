package com.comeato.Fragment.Offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.application.comeato.Adapters.OffersAdapter

import com.application.comeato.databinding.FragmentOffersBinding


class OffersFragment : Fragment() {
    val binding by lazy {
        FragmentOffersBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Initializer()
        return binding.root
    }

    private fun Initializer() {

        binding.rvOffers.adapter = OffersAdapter()

    }

}
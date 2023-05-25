package com.comeato.Fragment.Offers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.application.comeato.Adapters.OffersAdapter
import com.application.comeato.Fragment.Offers.OffersRepository
import com.application.comeato.Fragment.Offers.OffersViewModel
import com.application.comeato.Fragment.Offers.OffersViewModelFactory
import com.application.comeato.Interfaces.FragmentLoadCallBack
import com.application.comeato.R

import com.application.comeato.databinding.FragmentOffersBinding
import com.comeato.Utilities.MyApp


class OffersFragment(val isBackBtn: Boolean = false) : Fragment(), View.OnClickListener {
    val binding by lazy {
        FragmentOffersBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        OffersRepository(MyApp.MySingleton.getApiInterface(),requireContext())
    }

    private val viewModel by lazy {
        ViewModelProvider(this,OffersViewModelFactory(repository))[OffersViewModel::class.java]
    }

    private val offersAdapter by lazy {
        OffersAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Initializer()
        return binding.root
    }

    private fun Initializer()
    {
        binding.clNoItemFound.clLayout.visibility=View.GONE

        if (isBackBtn) {
            binding.ilTitleBar.imgBack.visibility = View.VISIBLE
        }
        binding.ilTitleBar.imgBack.setOnClickListener(this)


        //item not found layout
        binding.clNoItemFound.img.setImageDrawable(context?.getDrawable(R.drawable.ic_offer))
        binding.clNoItemFound.txtItem1.text = getString(R.string.sorry_no_offer_found)
        binding.clNoItemFound.txtItem2.text = getString(R.string.at_present_there_is_no_offer_available)


        binding.rvOffers.adapter = offersAdapter

        binding.swipeRefresh.setOnRefreshListener {
            repository.getOffers(false)
        }


        getData()
    }

    private fun getData() {
        viewModel.offers.observe(viewLifecycleOwner){
            binding.swipeRefresh.isRefreshing=false
            offersAdapter.submitList(it.offers)
            if(it.offers.size>0)
            {
                LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(Intent("offerFragment"))
                binding.clNoItemFound.clLayout.visibility=View.GONE
            }else
            {
                binding.clNoItemFound.clLayout.visibility=View.VISIBLE
            }
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imgBack -> activity?.onBackPressedDispatcher!!.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }

}
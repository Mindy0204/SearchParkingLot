package com.mindyhsu.searchparkinglot.parkinglot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.databinding.FragmentLoginBinding
import com.mindyhsu.searchparkinglot.databinding.FragmentParkingLotBinding
import com.mindyhsu.searchparkinglot.ext.getVmFactory
import com.mindyhsu.searchparkinglot.login.LoginViewModel
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.lang.NullPointerException

class ParkingLotFragment : Fragment() {
    private val viewModel by viewModels<ParkingLotViewModel> { getVmFactory() }
    private lateinit var binding: FragmentParkingLotBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParkingLotBinding.inflate(inflater, container, false)

        val adapter = ParkingLotAdapter()
        binding.parkingLotInfoRecyclerView.adapter = adapter

        viewModel.parkingLotInfo.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}
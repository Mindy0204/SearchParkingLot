package com.mindyhsu.searchparkinglot.parkinglot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindyhsu.searchparkinglot.databinding.FragmentParkingLotBinding
import com.mindyhsu.searchparkinglot.ext.getVmFactory
import com.mindyhsu.searchparkinglot.userupdate.UserUpdateFragmentDirections

class ParkingLotFragment : Fragment() {
    private val viewModel by viewModels<ParkingLotViewModel> { getVmFactory() }
    private lateinit var binding: FragmentParkingLotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParkingLotBinding.inflate(inflater, container, false)

        val adapter = ParkingLotAdapter()
        binding.parkingLotInfoRecyclerView.adapter = adapter

        viewModel.parkingLotInfo.observe(viewLifecycleOwner) {
            binding.parkingLotSwipeRefresh.isRefreshing = false
            adapter.submitList(it)
        }

        binding.parkingLotTimeZoneButton.setOnClickListener {
            findNavController().navigate(UserUpdateFragmentDirections.navigateToUserUpdateFragment())
        }

        // Finish app when pressing back twice
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!viewModel.isFinishApp) {
                        viewModel.isFinishApp = true
                    } else {
                        activity?.finish()
                    }
                }
            }
        )

        binding.parkingLotSwipeRefresh.setOnRefreshListener {
            viewModel.getParkingLotInfo()
        }

        return binding.root
    }
}

package com.mindyhsu.searchparkinglot.userupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.databinding.FragmentUserUpdateBinding
import com.mindyhsu.searchparkinglot.ext.getVmFactory
import com.mindyhsu.searchparkinglot.login.UserManager
import com.mindyhsu.searchparkinglot.network.LoadApiStatus
import timber.log.Timber


class UserUpdateFragment : Fragment() {
    private val viewModel by viewModels<UserUpdateViewModel> { getVmFactory() }
    private lateinit var binding: FragmentUserUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserUpdateBinding.inflate(inflater, container, false)

        binding.userUpdateToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.userUpdateEmailText.text = getString(R.string.user_update_email, UserManager.email)

        // Get system supported time zone option for drop down menu
        viewModel.timeZoneList.observe(viewLifecycleOwner) {
            val adapter = context?.let { context ->
                ArrayAdapter(context, R.layout.list_item_drop_down, it)
            }
            binding.userUpdateTimeZoneChoice.setAdapter(adapter)
        }

        // User chose time zone
        binding.userUpdateTimeZoneChoice.onItemClickListener =
            OnItemClickListener { _, _, item, _ ->
                viewModel.selectTimeZone(item)
                binding.userUpdateSetTimeZoneButton.visibility = View.VISIBLE
            }

        // Set time zone
        viewModel.gmt.observe(viewLifecycleOwner) { gmt ->
            binding.userUpdateSetTimeZoneButton.setOnClickListener {
                viewModel.updateUserTimeZone(gmt)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == LoadApiStatus.DONE) {
                Toast.makeText(
                    context,
                    R.string.user_update_set_time_zone_success,
                    Toast.LENGTH_SHORT
                ).show()
                binding.userUpdateSetTimeZoneButton.visibility = View.GONE
            } else if (it == LoadApiStatus.ERROR) {
                Toast.makeText(
                    context,
                    viewModel.error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }
}
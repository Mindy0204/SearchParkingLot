package com.mindyhsu.searchparkinglot.userupdate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.databinding.FragmentUserUpdateBinding
import com.mindyhsu.searchparkinglot.ext.getVmFactory
import com.mindyhsu.searchparkinglot.login.UserManager

class UserUpdateFragment : Fragment() {
    private val viewModel by viewModels<UserUpdateViewModel> { getVmFactory() }
    private lateinit var binding: FragmentUserUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserUpdateBinding.inflate(inflater, container, false)

        binding.userUpdateToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.userUpdateEmailText.text = getString(R.string.user_update_email, UserManager.email)

        return binding.root
    }
}
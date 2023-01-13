package com.mindyhsu.searchparkinglot.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.databinding.FragmentLoginBinding
import com.mindyhsu.searchparkinglot.ext.getVmFactory

class LoginFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEditText.text.toString()
            val password = binding.loginPasswordEditText.text.toString()

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.isNotEmpty()) {
                    viewModel.login(email, password)
                } else {
                    Toast.makeText(context, getString(R.string.password_empty), Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, getString(R.string.email_not_valid), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(LoginFragmentDirections.navigateToParkingLotFragment())
            } else {
                Toast.makeText(context, getString(R.string.email_password_wrong), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }
}
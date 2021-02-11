package com.katilijiwo.ministockbitapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.katilijiwo.ministockbitapp.R
import com.katilijiwo.ministockbitapp.base.BaseFragment
import com.katilijiwo.ministockbitapp.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun setListener() {
        super.setListener()
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> {
                if(viewModel.validateLogin()){
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWatchListFragment())
                } else {
                    Toast.makeText(requireContext(), viewModel.getErrorMessage(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
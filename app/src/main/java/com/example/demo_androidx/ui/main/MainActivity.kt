package com.example.demo_androidx.ui.main

import android.os.Bundle
import android.widget.Toast
import com.example.demo_androidx.Adapter
import com.example.demo_androidx.R
import com.example.demo_androidx.base.BaseActivity
import com.example.demo_androidx.databinding.ActivityMainBinding
import com.example.demo_androidx.di.ViewModelFactory
import com.example.demo_androidx.repository.errormanager.ErrorManager
import com.example.demo_androidx.repository.model.Resource
import com.example.demo_androidx.repository.model.User
import com.example.demo_androidx.repository.model.response.ListUserResponse
import com.example.demo_androidx.utils.observe
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun modelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initializeViewModel() {
        viewModel.loadUsers()
        binding.src = R.mipmap.ic_launcher
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun observeViewModel() {
        observe(viewModel.getUsers(), this::handleListUsers)
    }

    private fun handleListUsers(result: Resource<ListUserResponse>) {
        when (result) {
            is Resource.Loading -> showIndicator()
            is Resource.HideLoading -> hideIndicator()
            is Resource.DataError -> {
                hideIndicator()
                result.errorCode?.let {
                    Toast.makeText(this, ErrorManager.getError(it).message, Toast.LENGTH_SHORT).show()
                }
            }
            is Resource.Success -> {
                hideIndicator()
                if (adapter == null) {
                    adapter = Adapter()
                    binding.rcv.setHasFixedSize(true)
                    binding.rcv.adapter = adapter
                }
                result.data?.let {
                    adapter?.update(it.data)
                }
            }
        }
    }
}


package com.example.demo_androidx.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.demo_androidx.R
import com.example.demo_androidx.constants.RemoteCode
import com.example.demo_androidx.di.ViewModelFactory
import com.example.demo_androidx.repository.errormanager.ErrorManager
import com.example.demo_androidx.utils.observe
import com.example.demo_androidx.utils.toast
import dagger.android.AndroidInjection
import javax.inject.Inject


abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: T
    protected lateinit var binding: B
    private var dialog: AlertDialog? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = viewModelFactory.create(modelClass())
        setContentView(binding.root)
        initializeViewModel()
        observeViewModel()
        handleError()
    }

    protected abstract fun modelClass(): Class<T>
    protected abstract fun initializeViewModel()
    abstract fun getViewBinding(): B
    abstract fun observeViewModel()

    fun handleError() {
        observe(viewModel.errorListener) {
            when (it) {
                RemoteCode.NO_INTERNET_CONNECTION,
                RemoteCode.NETWORK_ERROR -> ErrorManager.getError(it).toast(this)
            }
        }
    }

    fun showIndicator() {
        if (dialog == null) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(false)
            builder.setView(R.layout.layout_indicator)
            dialog = builder.create().apply {
                setCanceledOnTouchOutside(false)
            }
        }
        dialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideIndicator() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}

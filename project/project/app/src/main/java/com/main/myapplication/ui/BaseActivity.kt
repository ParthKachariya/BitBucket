package com.main.myapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val TAG: String = this.javaClass.simpleName

    abstract fun getLayoutId(): Int

    lateinit var binding: T

    val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        mCompositeDisposable.dispose()

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }
}
package com.example.demo_androidx

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.demo_androidx.di.DaggerAppComponent
//import com.example.demo_androidx.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class App : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initDagger()
    }

    private fun initDagger() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
package com.example.demo_androidx.di

import com.example.demo_androidx.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
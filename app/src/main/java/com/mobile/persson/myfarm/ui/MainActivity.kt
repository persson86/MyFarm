package com.mobile.persson.myfarm.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.widget.Toast
import com.mobile.persson.myfarm.R
import com.mobile.persson.myfarm.base.BaseLifecycleActivity
import com.mobile.persson.myfarm.data.model.PlantModel


class MainActivity : BaseLifecycleActivity<PlantViewModel>() {

    override val viewModelClass = PlantViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.plantLiveData.observe(this, Observer<Map<String, PlantModel>> {
            it?.let {
                viewModel.showPlants(this, it)
            }
        })
        viewModel.throwableLiveData.observe(this, Observer<Throwable> {
            it?.let {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

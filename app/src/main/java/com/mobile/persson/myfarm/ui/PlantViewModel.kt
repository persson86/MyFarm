package com.mobile.persson.myfarm.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.mobile.persson.myfarm.data.model.PlantModel
import com.mobile.persson.myfarm.repository.PlantRepository

open class PlantViewModel(application: Application?) : AndroidViewModel(application) {

    private val repository = PlantRepository()
    val resultLiveData = PlantLiveData(repository)
    val throwableLiveData = MediatorLiveData<Throwable>()
    val plantLiveData = MediatorLiveData<Map<String, PlantModel>>()

    init {
        throwableLiveData.addSource(resultLiveData) {
            it?.second?.let { throwableLiveData.value = it }
        }
    }

    init {
        plantLiveData.addSource(resultLiveData) {
            it?.first?.let { plantLiveData.value = it }
        }
    }
}

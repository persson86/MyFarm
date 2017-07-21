package com.mobile.persson.myfarm.ui

import android.arch.lifecycle.MediatorLiveData
import com.mobile.persson.myfarm.data.model.PlantModel
import com.mobile.persson.myfarm.repository.PlantRepository
import io.reactivex.disposables.Disposable

class PlantLiveData(repository: PlantRepository) : MediatorLiveData<Pair<Map<String, PlantModel>?, Throwable?>>() {

    private var disposable: Disposable? = null

    init {
        disposable = repository
                .getPlants()
                .subscribe { data, error -> this@PlantLiveData.value = Pair(data, error) }
    }

    override fun onInactive() {
        super.onInactive()
        if (disposable?.isDisposed?.not() ?: false) {
            disposable?.dispose()
        }
    }
}
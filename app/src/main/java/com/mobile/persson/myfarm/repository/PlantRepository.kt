package com.mobile.persson.myfarm.repository

import android.util.Log
import com.mobile.persson.myfarm.data.RemoteData
import com.mobile.persson.myfarm.data.model.PlantModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.reactivestreams.Subscriber

class PlantRepository : RepositoryData {
    private val remoteData = RemoteData.Factory.create()

    override fun getPlants(): Single<Map<String, PlantModel>>
            = remoteData
            .getPlants()
            .doOnSuccess {
                Log.v("LFSP", "OK")
            }
            .doOnError {
                Log.v("LFSP", "OK")
            }
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
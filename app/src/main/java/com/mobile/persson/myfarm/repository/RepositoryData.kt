package com.mobile.persson.myfarm.repository

import com.mobile.persson.myfarm.data.model.PlantModel
import io.reactivex.Observable
import io.reactivex.Single

interface RepositoryData {
    fun getPlants(): Single<Map<String, PlantModel>>
}
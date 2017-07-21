package com.mobile.persson.myfarm.data

import com.mobile.persson.myfarm.data.model.PlantModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RemoteData {
    @GET("plant_list.json")
    fun getPlants(): Single<Map<String, PlantModel>>

    companion object Factory {
        fun create(): RemoteData {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://agro-horta.firebaseio.com/database/language_en/")
                    .build()

            return retrofit.create(RemoteData::class.java)
        }
    }
}
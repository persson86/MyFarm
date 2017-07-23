package com.mobile.persson.myfarm.ui

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import com.mobile.persson.myfarm.R
import com.mobile.persson.myfarm.data.model.PlantModel
import com.mobile.persson.myfarm.repository.PlantRepository
import kotlinx.android.synthetic.main.content_main.*


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

    fun showPlants(context: Context, plants: Map<String, PlantModel>) {
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://agro-horta.appspot.com")
        val storageChildRef = storageRef.child("images")
        val activity = context as Activity

        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        params.width = 360

/*        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels
         val scaleWidth = dm.scaledDensity / 5*/

        /*      val displayMetrics = DisplayMetrics()
              activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
              val height = displayMetrics.heightPixels
              val width = displayMetrics.widthPixels*/

/*        val display = activity.getWindowManager().getDefaultDisplay()
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = activity.getResources().getDisplayMetrics().density
        val dpHeight = outMetrics.heightPixels / density
        val dpWidth = outMetrics.widthPixels / density*/

        for (plant in plants) {
            val card = CardView(context)
            card.layoutParams = params
            card.setContentPadding(15, 30, 15, 30)
            card.setCardBackgroundColor(Color.BLACK)

            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = params
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.minimumWidth = 200
            linearLayout.minimumHeight = 400

            val tvName = TextView(context)
            tvName.layoutParams = params
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
            tvName.setTextColor(Color.WHITE)
            tvName.text = plant.value.plantName
            linearLayout.addView(tvName)

            val ivImage = ImageView(context)
            Glide.with(context)
                    .using(FirebaseImageLoader())
                    .load(storageChildRef.child(plant.value.plantImage!!))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivImage)
            linearLayout.addView(ivImage)

            card.setOnClickListener {
                Toast.makeText(context, plant.value.plantName, Toast.LENGTH_SHORT).show()
            }

            card.addView(linearLayout)
            activity.llContent.addView(card)
        }
    }

    /*fun showPlants(context: Context, plants: Map<String, PlantModel>) {
        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://agro-horta.appspot.com")
        val storageChildRef = storageRef.child("images")

        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val activity = context as Activity

        for (plant in plants) {
            val card = CardView(context)
            card.layoutParams = params
            card.setContentPadding(15, 30, 15, 30)
            card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"))

            val ll = LinearLayout(context)
            ll.layoutParams = params
            ll.orientation = LinearLayout.VERTICAL
            ll.minimumWidth = 200
            ll.minimumHeight = 400

            val tvName = TextView(context)
            tvName.layoutParams = params
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)
            tvName.setTextColor(Color.RED)
            tvName.text = plant.value.plantName
            ll.addView(tvName)

            val ivImage = ImageView(context)
            Glide.with(context)
                    .using(FirebaseImageLoader())
                    .load(storageChildRef.child(plant.value.plantImage!!))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivImage)
            ll.addView(ivImage)

            card.setOnClickListener {
                Toast.makeText(context, plant.value.plantName, Toast.LENGTH_SHORT).show()
            }

            card.addView(ll)
            activity.llContent.addView(card)
        }
    }*/
}

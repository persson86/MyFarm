package com.mobile.persson.myfarm

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mobile.persson.myfarm.base.BaseLifecycleActivity
import com.mobile.persson.myfarm.data.model.PlantModel
import com.mobile.persson.myfarm.ui.PlantViewModel
import kotlinx.android.synthetic.main.activity_main.*


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
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()
                createCardView(it)
            }
        })
        viewModel.throwableLiveData.observe(this, Observer<Throwable> {
            it?.let {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createCardView(plants: Map<String, PlantModel>) {

        for (plant in plants) {

            val card = CardView(this)

            // Set the CardView layoutParams
            val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            card.setLayoutParams(params)

            // Set cardView content padding
            card.setContentPadding(15, 15, 15, 15)

            // Set a background color for CardView
            card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"))

            // Initialize a new TextView to put in CardView
            val tvName = TextView(this)
            tvName.layoutParams = params
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30f)
            tvName.setTextColor(Color.RED)
            tvName.text = plant.value.plantName

            // Put the TextView in CardView
            card.addView(tvName)

            // Finally, add the CardView in root layout
            llContent.addView(card)
        }
    }
}

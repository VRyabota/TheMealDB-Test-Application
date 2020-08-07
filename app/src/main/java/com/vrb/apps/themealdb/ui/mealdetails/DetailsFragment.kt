package com.vrb.apps.themealdb.ui.mealdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.utils.Constants.DATA_MEAL_DETAILS
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsToolbar.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, null)
        detailsToolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val selectedMeal =
            try {
                Gson().fromJson(requireArguments().getString(DATA_MEAL_DETAILS), Meal::class.java)
            } catch (e: Exception) {
                null
            }


        selectedMeal?.let { meal ->

            Glide.with(requireContext()).load(meal.imageLink).into(detailsMealImage)

            detailsMealName.text = meal.name
            detailsMealCategory.text = getString(R.string.meal_category, meal.category)
            detailsMealArea.text = getString(R.string.meal_area, meal.area)
            detailsMealInstructions.text = meal.instructions

            val adapter = IngredientsAdapter(requireContext(), meal.ingredients)
            detailsMealIngredientsRecycler.setHasFixedSize(true)
            detailsMealIngredientsRecycler.layoutManager = LinearLayoutManager(context)
            detailsMealIngredientsRecycler.isVerticalScrollBarEnabled = false
            detailsMealIngredientsRecycler.adapter = adapter

            detailsMealYoutubeLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(meal.youtubeLink)
                startActivity(intent)
            }
        }
    }
}
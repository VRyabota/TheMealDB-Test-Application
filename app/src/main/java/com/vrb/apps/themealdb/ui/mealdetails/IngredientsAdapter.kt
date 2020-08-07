package com.vrb.apps.themealdb.ui.mealdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.data.models.Ingredient
import com.vrb.apps.themealdb.utils.UniversalViewHolder
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsAdapter(
    private val context: Context,
    private val items: List<Ingredient>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UniversalViewHolder(layoutInflater.inflate(R.layout.item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ingredient = items[position]

        with(holder.itemView) {
            this.itemIngredientName.text = ingredient.name
            this.itemIngredientMeasure.text = ingredient.measure
        }
    }
}
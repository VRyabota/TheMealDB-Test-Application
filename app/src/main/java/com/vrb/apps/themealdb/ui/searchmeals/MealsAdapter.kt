package com.vrb.apps.themealdb.ui.searchmeals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.utils.UniversalViewHolder
import kotlinx.android.synthetic.main.item_meal.view.*

class MealsAdapter(
    private val context: Context,
    private val mealClickAction: (meal: Meal) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private val glide = Glide.with(context)

    private val items: MutableList<Meal> = mutableListOf()

    fun setData(meals: List<Meal>) {
        items.clear()
        items.addAll(meals)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_meal, parent, false)
        val viewHolder = UniversalViewHolder(view)
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            mealClickAction(items[position])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meal = items[position]

        with(holder.itemView) {

            this.visibility = View.VISIBLE
            /*this.post {
                val lp = this.layoutParams
                lp.height = this.width
                this.layoutParams = lp
            }*/

            glide.load(meal.imageLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this.itemMealImage)

            this.itemMealName.text = meal.name
            this.itemMealCategory.text = context.getString(R.string.meal_category, meal.category)
            this.itemMealArea.text = meal.area
        }
    }
}
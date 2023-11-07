  package com.example.foodapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodapp.databinding.CatagoryFoodRowLayoutBinding
import com.example.foodapp.model.Category
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealDetailItem

  class CategoryFoodsAdapter(private val meals: List<Meal>,val listener: CategoryFoodsAdapter.Listener) :
    RecyclerView.Adapter<CategoryFoodsAdapter.FoodViewHolder>() {

    interface Listener {
        fun onItemClick(mealDetail: Meal)
    }

    inner class FoodViewHolder(val itemBinding: CatagoryFoodRowLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(meal: Meal) {
            itemBinding.foodNameTextView.text = meal.strMeal
            //itemBinding.foodImageView.text = meal.strMealThumb

            Glide
                .with(itemBinding.root.context)
                .load(meal.strMealThumb)
                .apply(RequestOptions().override(1500,150))
                .into(itemBinding.foodImageView)

            // Glide kullanılacak, tekrar bak buraya
            //itemView.foodidMeal.text = meal.idmeal

            itemBinding.foodImageView.setOnClickListener {
                listener.onItemClick(meal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemBinding = CatagoryFoodRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(itemBinding)
    }

    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(view)
    }
     */

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int {
        return meals.size
    }
}
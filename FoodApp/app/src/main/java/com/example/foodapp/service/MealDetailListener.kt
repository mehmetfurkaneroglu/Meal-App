package com.example.foodapp.service

import com.example.foodapp.model.MealDetails

interface MealDetailListener {
    fun onMealDetailLoaded(mealDetails: MealDetails)
    fun onError(error: Throwable)
}

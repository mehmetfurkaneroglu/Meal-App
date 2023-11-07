package com.example.foodapp.service

import com.example.foodapp.model.CategoryEntity
import com.example.foodapp.model.CategoryFoods
import com.example.foodapp.model.MealDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {
    //www.themealdb.com/api/json/v1/1/
    //categories.php
    @GET("categories.php")
    fun getCategories(): Call<CategoryEntity> // CategoryEntity API yanıtını temsil eder

    //filter.php?c=Seafood
    @GET("filter.php?")
    fun getCategoryFoods(@Query("c") categoryName:String):Call<CategoryFoods>

    //lookup.php?i=52772
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") mealDetail:String): Call<MealDetails>

}



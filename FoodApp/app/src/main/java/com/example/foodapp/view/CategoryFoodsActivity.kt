package com.example.foodapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.adapter.CategoryFoodsAdapter
import com.example.foodapp.adapter.RecyclerViewAdapter
import com.example.foodapp.databinding.ActivityCategoryFoodsBinding
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.model.Category
import com.example.foodapp.model.CategoryEntity
import com.example.foodapp.model.CategoryFoods
import com.example.foodapp.model.Meal
import com.example.foodapp.model.MealDetails
import com.example.foodapp.service.FoodAPI
import com.example.foodapp.util.utils.CATEGORY_NAME
import com.example.foodapp.util.utils.MEAL_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryFoodsActivity : AppCompatActivity(), CategoryFoodsAdapter.Listener {

    lateinit var binding: ActivityCategoryFoodsBinding

    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private lateinit var categoryName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_category_foods) //bindingden dolayı çalışmıyor.
        binding = ActivityCategoryFoodsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        categoryName = intent.getStringExtra(CATEGORY_NAME) ?: ""

        //hatalı tanımlama tek kullanırken bindingle çek
        //val recyclerView = binding.recyclerView // RecyclerView'ı Activity'nin layout'undan alın
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        prepareSeafoodList()
/*
        //setSupportActionBar(binding.toolBar)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
 */


    }

    private fun prepareSeafoodList() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FoodAPI::class.java)
        val call = service.getCategoryFoods(categoryName)

        //val call = service.getCategoryFoods() // bu hata veriyor onu sor

        call.enqueue(object : Callback<CategoryFoods> {
            override fun onResponse(call: Call<CategoryFoods>, response: Response<CategoryFoods>) {
                if (response.isSuccessful) {
                    val categoryFoods = response.body()
                    if (categoryFoods != null) {
                        // Yiyecekleri kullanabilirsiniz
                        // meals listesini RecyclerViewAdapter'a iletebilirsiniz

                        binding.recyclerView.adapter = CategoryFoodsAdapter(categoryFoods.meals,this@CategoryFoodsActivity)
                        binding.pageTwoBarName.text = categoryName
                        //floatingActionBar (sayfanın en üstüne gitme)
                        binding.floatingActionButton.setOnClickListener {
                            binding.recyclerView.smoothScrollToPosition(0)
                        }

                        // Geri butonuna tıklama işlevini tanımlayın
                        binding.backButton.setOnClickListener {
                            onBackPressed() // Aktiviteyi geri tuşuna basılmış gibi kapatır
                        }


                    }
                }
            }

            override fun onFailure(call: Call<CategoryFoods>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClick(mealDetail: Meal) {
        //Toast.makeText(this,"Clicked : ${mealDetail.idMeal}", Toast.LENGTH_SHORT ).show()
        /*
        Toast.makeText kullanılarak bir kısa mesaj (Toast) oluşturulur. Bu mesaj, tıklanan yemek öğesinin benzersiz kimliği (idMeal) ile birlikte görüntülenir.
         */
        val intent = Intent(this,MealDetailActivity::class.java)
        intent.putExtra(MEAL_ID,mealDetail.idMeal)
        startActivity(intent)
    }

}


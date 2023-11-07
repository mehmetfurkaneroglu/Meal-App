package com.example.foodapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityMealDetailBinding
import com.example.foodapp.model.MealDetails
import com.example.foodapp.service.FoodAPI
import com.example.foodapp.util.utils.MEAL_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealDetailActivity : AppCompatActivity() {

    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    //private lateinit var mealID: Int //lateinit sadece referans türler (örneğin sınıf veya nesne) için kullanılabilir, ilkel türler (int, float, boolean, vb.) için kullanılamaz.
    private lateinit var mealID: String


    lateinit var binding: ActivityMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
/*
        mealID= intent.getIntArrayExtra()
//categoryName = intent.getStringExtra(CATEGORY_NAME)?:""
        val intent = Intent(this, MainDetailActivity::class.java)
        startActivity(intent)
*/
        // Intent'ten mealID'yi al
        mealID = intent.getStringExtra(MEAL_ID) ?: ""
        // API'den yemek detaylarını yükle
        loadMealDetails()
//aladaldkasşldkasşld        //detay sayfasındaki metnin scroll özelliği
        binding.instructionText.movementMethod = ScrollingMovementMethod()
    }

    private fun loadMealDetails() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FoodAPI::class.java)
        val call = service.getMealDetails(mealID)


        call.enqueue(object : Callback<MealDetails> {
            override fun onResponse(call: Call<MealDetails>, response: Response<MealDetails>) {
                if (response.isSuccessful) {
                    //val categoryFoods = response.body()
                    //if (categoryFoods != null) {
                    val mealDetails = response.body()
                    if (mealDetails != null) {
                        //binding.textview.text = mealDetails.meals[0].strCategory
                        //mealDetails.meals[0].strImageSource

                        // Geri butonuna tıklama işlevini tanımlayın
                        binding.backButton.setOnClickListener {
                            onBackPressed() // Aktiviteyi geri tuşuna basılmış gibi kapatır
                        }

                        //Yemek adı
                        binding.pageThreeMealName.text = mealDetails.meals[0].strMeal

                        //Glide
                        Glide
                            .with(binding.root.context)
                            .load(mealDetails.meals[0].strMealThumb)
                            .centerCrop()
                            .into(binding.mealPicture)

                        //sildim binding.mealName.text = mealDetails.meals[0].strMeal
                        binding.areaName.text = mealDetails.meals[0].strArea
                        //binding.youtubeLink.text = mealDetails.meals[0].strYoutube
                        binding.youtubeLink.setOnClickListener {
                            val url = mealDetails.meals[0].strYoutube
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                        }


                        val myArrayIngredient = arrayListOf<String>()
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient1.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient2.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient3.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient4.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient5.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient6.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient7.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient8.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient9.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient10.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient11.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient12.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient13.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient14.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient15.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient16.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient17.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient18.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient19.toString())
                        myArrayIngredient.add(mealDetails.meals[0].strIngredient20.toString())

                        var combinedTextIngredient = ""
                        for (item in myArrayIngredient){
                            if (item.isNotEmpty() && item != "null"){
                                combinedTextIngredient += item + "\n"
                            }
                        }
                        binding.ingredientText.text = combinedTextIngredient
                        /*
                        // Verileri birleştirme ve bir metin oluşturma
                        val combinedTextIngredient = myArrayIngredient.joinToString("\n")
                        // ingredientText'e birleştirilmiş metni ayarlayma
                        binding.ingredientText.text = combinedTextIngredient
                         */


                        val myArrayMeasure = arrayListOf<String>()
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure1.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure2.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure3.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure4.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure5.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure6.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure7.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure8.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure9.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure10.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure11.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure12.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure13.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure14.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure15.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure16.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure17.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure18.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure19.toString())
                        myArrayMeasure.add(mealDetails.meals[0].strMeasure20.toString())

                        var combinedTextMeasure = ""
                        for (item in myArrayMeasure){
                            if (item.isNotEmpty() && item != "null" && item != " "){
                                combinedTextMeasure += item + "\n"
                            }
                        }
                        binding.measureText.text = combinedTextMeasure
                        /*
                        // Verileri birleştirme ve bir metin oluşturma
                        val combinedTextMeasure = myArrayMeasure.joinToString("\n")
                        // measureText'e birleştirilmiş metni ayarlama
                        binding.measureText.text = combinedTextMeasure
                        */




                        /*
                        val myArray = ArrayList<String>()

                        for (i in 1..20) {
                            val ingredient = mealDetails.meals[0]["strIngredient${i}"] as? String
                            val measure = mealDetails.meals[0]["strMeasure${i}"] as? String
                            // bu şekilde başka veri olduğunu anlıyorduk.

                            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                                val ingredientAndMeasure = "$ingredient: $measure"
                                myArray.add(ingredientAndMeasure)
                            }
                        }*/
/*
                        val myArray = arrayListOf<String>()

                        for (i in 1..20) {
                            val ingredient = mealDetails.meals[0]["strIngredient${i}"]?.toString()
                            val measure = mealDetails.meals[0]["strMeasure$i"]?.toString()

                            if (ingredient != null && measure != null) {
                                val ingredientAndMeasure = "$ingredient - $measure"
                                myArray.add(ingredientAndMeasure)
                            }
                        }

 */


                        //binding.drinkAlternative.text = mealDetails.meals[0].strDrinkAlternate
                        //val drinkAlternate = mealDetails.meals[0].strDrinkAlternate
                        //binding.drinkAlternative.text = drinkAlternate ?: "Drink alternate not available"
                        val drinkAlternate = mealDetails.meals[0].strDrinkAlternate

                        if (drinkAlternate.isNullOrEmpty().not()) {
                            binding.drinkAlternative.visibility = View.VISIBLE
                            binding.drinkAlternativeText.visibility = View.VISIBLE
                            binding.drinkAlternative.text = drinkAlternate
                        } else {
                            binding.drinkAlternative.visibility = View.GONE
                            binding.drinkAlternativeText.visibility = View.GONE
                        }

// bu satıra tekrardan bak
                        binding.instructionText.text = mealDetails.meals[0].strInstructions

                        //val scrollableTextView = mealDetails.meals[0].strInstructions
                        //binding.instructionText.text= scrollableTextView
/*
                        binding.ingredientText1.text = mealDetails.meals[0].strIngredient1
                        binding.measureText1.text = mealDetails.meals[0].strMeasure1
                        binding.ingredientText2.text = mealDetails.meals[0].strIngredient2
                        binding.ingredientText3.text = mealDetails.meals[0].strIngredient3
                        binding.ingredientText4.text = mealDetails.meals[0].strIngredient4
                        binding.ingredientText5.text = mealDetails.meals[0].strIngredient5
                        binding.ingredientText6.text = mealDetails.meals[0].strIngredient6
                        binding.ingredientText7.text = mealDetails.meals[0].strIngredient7
                        binding.ingredientText8.text = mealDetails.meals[0].strIngredient8
                        binding.ingredientText9.text = mealDetails.meals[0].strIngredient9
                        binding.ingredientText10.text = mealDetails.meals[0].strIngredient10


 */

                        //listener oluştur, activitye gir
                        //???????????????
                        // Listener'ı kullanarak verileri iletebilirsiniz
                        //onMealDetailLoaded(mealDetails)



                    }
                    /*else {
                        // Hata durumunda listener'ı kullanarak hatayı iletebilirsiniz
                        onError(Throwable("API response error"))
                    }*/
                }
            }

            override fun onFailure(call: Call<MealDetails>, t: Throwable) {
                t.printStackTrace()

                // Hata durumunda listener'ı kullanarak hatayı iletebilirsiniz
                //onError(t)
            }
        })
    }



}

package com.example.foodapp.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.adapter.RecyclerViewAdapter
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.model.Category
import com.example.foodapp.model.CategoryEntity
import com.example.foodapp.service.FoodAPI
import com.example.foodapp.util.utils.CATEGORY_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    lateinit var binding: ActivityMainBinding

    //URL BAZ: https://raw.githubusercontent.com/
    //GERİ KALAN: atilsamancioglu/K21-JSONDataSet/master/crypto.json
    private val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    private var categoryList: ArrayList<Category>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) //bindingden dolayı çalışmıyor.
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //RecycleView

        //loadCategories()
        // internet olup olmamasını kontrol ediyorum
        if (isNetworkAvailable(this)) {
            // İnternet bağlantısı varsa kategorileri yükle
            loadCategories()
        } else {
            // İnternet bağlantısı yoksa hata mesajı göster
            showNoInternetMessage()
        }

/*
        //setSupportActionBar(binding.toolBar)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
*/
    }

    private fun loadCategories() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FoodAPI::class.java)
        val call = service.getCategories()

        call.enqueue(object : Callback<CategoryEntity> {
            override fun onResponse(
                call: Call<CategoryEntity>,
                response: Response<CategoryEntity>
            ) {
                if (response.isSuccessful) {
                    val categoryEntity = response.body()
                    if (categoryEntity != null) {
                        val categories = categoryEntity.categories // Kategorileri alın

                        // Burada kategorileri kullanabilirsiniz, örneğin RecyclerViewAdapter'a iletebilirsiniz
                        recyclerViewAdapter = RecyclerViewAdapter(categories, this@MainActivity)
                        binding.recyclerView.adapter = recyclerViewAdapter

                        //floatingActionBar (sayfanın en üstüne gitme)
                        binding.floatingActionButton.setOnClickListener {
                            binding.recyclerView.smoothScrollToPosition(0)
                        }

                        binding.exitButton.setOnClickListener {
                            onBackPressed()
                        }



                    }
                }
            }

            override fun onFailure(call: Call<CategoryEntity>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClick(category: Category) {
        //Toast.makeText(this,"Clicked : ${category.idCategory}",Toast.LENGTH_SHORT ).show()
        /*
        Toast.makeText kullanılarak bir kısa mesaj (Toast) oluşturulur. Bu mesaj, tıklanan yemek öğesinin benzersiz kimliği (idMeal) ile birlikte görüntülenir.
         */
        val intent = Intent(this,CategoryFoodsActivity::class.java)
        intent.putExtra(CATEGORY_NAME,category.strCategory)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to exit the application?")//Uygulamadan çıkmak istiyor musunuz?
        builder.setPositiveButton("YES") { _, _ ->
            super.onBackPressed() // Uygulamadan çıkış işlemi
        }
        builder.setNegativeButton("NO") { dialog, _ ->
            dialog.dismiss() // İptal
        }
        builder.show()
    }

    // internet olup olmamasını kontrol ediyorum

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun showNoInternetMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet Connection")
        builder.setMessage("Please check your internet connection and try again.")
        builder.setNegativeButton("OK") { dialog, _ ->
            this@MainActivity.finish()
        }
        builder.setPositiveButton("Retry") { dialog, _ ->
            if (isNetworkAvailable(this).not()) {
                dialog.dismiss()
                showNoInternetMessage()
            } else {
                loadCategories()
            }
        }
        builder.show()
    }


}

/*

        /*
        call.enqueue(object: Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let{//eğerki bu boş gelmediyse
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let{
                            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity )
                            binding.recyclerView.adapter = recyclerViewAdapter

                        }
/*
//logcatta verileri görmek için , System.out
                        for(cryptoModel: CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }
*/
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace( )
            }

        })

         */

        call.enqueue(object: Callback<CategoryEntity> {
            override fun onResponse(
                call: Call<CategoryEntity>,
                response: Response<CategoryEntity>
            ) {
                if(response.isSuccessful){
                    response.body()?.categories
                }
            }

            override fun onFailure(call: Call<CategoryEntity>, t: Throwable) {
                t.printStackTrace( )
            }

        })
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}",Toast.LENGTH_LONG ).show()
    }
}

 */
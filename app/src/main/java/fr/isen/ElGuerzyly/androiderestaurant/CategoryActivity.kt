package fr.isen.ElGuerzyly.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.ElGuerzyly.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.ElGuerzyly.androiderestaurant.model.Dish
import fr.isen.ElGuerzyly.androiderestaurant.model.RequestResult
import org.json.JSONObject

const val DISH = "dish"

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryButtonBack.setOnClickListener {
            finish()
        }


        val category = intent.getStringExtra(TITLE_CATEGORY)
        binding.categoryTitle.text = category
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        loadDishesFromCategory(category)
    }

    private fun loadDishesFromCategory(category : String?) {
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val params = HashMap<String, Number>()
        params["id_shop"] = 1
        val jsonObject = JSONObject(params as Map<*, *>)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            {
                val json = Gson().fromJson(it.toString(), RequestResult::class.java)
                display(json.data.firstOrNull{dish-> dish.name_fr == category}?.items ?: listOf())
            }, {
                Log.e("API", it.toString())
                Toast.makeText(this, "API request failed", Toast.LENGTH_SHORT).show()
                finish()
            })



        Volley.newRequestQueue(this).add(request)
    }

    private fun display(dishesList : List<Dish>) {
        binding.categoryList.adapter = DishAdapter(dishesList) {
            val intent = Intent(this, DetailsDishActivity::class.java).apply {
                putExtra(DISH, it)
            }
            startActivity(intent)
        }
    }
}
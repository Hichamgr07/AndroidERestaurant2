package fr.isen.ElGuerzyly.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.ElGuerzyly.androiderestaurant.databinding.ActivityDetailsDishBinding
import fr.isen.ElGuerzyly.androiderestaurant.model.BasketList
import fr.isen.ElGuerzyly.androiderestaurant.model.Dish
import fr.isen.ElGuerzyly.androiderestaurant.model.DishBasket
import java.io.File

class DetailsDishActivity : MenuActivity() {
    private lateinit var binding: ActivityDetailsDishBinding
    private lateinit var dish: Dish

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dishButtonBack.setOnClickListener {
            finish()
        }

        dish = intent.getSerializableExtra(DISH) as Dish
        binding.dishTitle.text = dish.name_fr
        val txt = "Total : ${dish.prices[0].price} €"
        binding.dishPriceButton.text = txt

        var quantity : Int = 1
        binding.dishQuantity.text = quantity.toString()

        binding.dishLessButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityPrice(quantity)
            }
        }


        binding.dishMoreButton.setOnClickListener {
            quantity++
            updateQuantityPrice(quantity)
        }


        binding.dishPriceButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.addToBasket), Toast.LENGTH_SHORT).show()
            updateFile(DishBasket(dish, quantity))
            updateSharedPreferences(quantity, (dish.prices[0].price.toFloat() * quantity))
            finish()
        }


        var ingredients = ""
        for (i in dish.ingredients) {
            ingredients += (i.name_fr + ", ")
        }
        binding.dishIngredients.text = ingredients

        val carouselView = binding.dishImage
        with(carouselView) {
            pageCount = dish.images.size
            setImageListener { position, imageView ->
                if (dish.images[0].isNotEmpty()) {
                    Picasso.get()
                        .load(dish.images[position])
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .fit()
                        .into(imageView)
                } else {
                    Picasso.get()
                        .load(R.drawable.logo)
                        .into(imageView)
                }
            }
        }


    }
     private fun updateQuantityPrice(quantity: Int) {
        binding.dishQuantity.text = quantity.toString()

        val price = dish.prices[0].price.toFloat()
        val totalPrice = "Total : ${price * quantity} €"
        binding.dishPriceButton.text = totalPrice
    }

    private fun updateFile(dishBasket : DishBasket) {
        val file = File(cacheDir.absolutePath + "/basket.json")
        var dishesBasket: List<DishBasket> = ArrayList()

        if (file.exists()) {
            dishesBasket = Gson().fromJson(file.readText(), BasketList::class.java).data
        }

        var dupli = false
        for (i in dishesBasket.indices) {
            if (dishesBasket[i].dish == dishBasket.dish) {
                dishesBasket[i].quantity += dishBasket.quantity
                dupli = true
            }
        }

        if (!dupli) {
            dishesBasket = dishesBasket + dishBasket
        }

        file.writeText(Gson().toJson(BasketList(dishesBasket)))
    }

    private fun updateSharedPreferences(quantity: Int, price: Float) {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE)

        val oldQuantity = sharedPreferences.getInt(getString(R.string.spTotalQuantity), 0)
        val newQuantity = oldQuantity + quantity
        sharedPreferences.edit().putInt(getString(R.string.spTotalQuantity), newQuantity).apply()

        val oldPrice = sharedPreferences.getFloat(getString(R.string.spTotalPrice), 0.0f)
        val newPrice = oldPrice + price
        sharedPreferences.edit().putFloat(getString(R.string.spTotalPrice), newPrice).apply()
    }
}

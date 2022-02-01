package fr.isen.ElGuerzyly.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.ElGuerzyly.androiderestaurant.databinding.ActivityDetailsDishBinding
import fr.isen.ElGuerzyly.androiderestaurant.model.Dish

class DetailsDishActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dishButtonBack.setOnClickListener {
            finish()
        }

        val dish : Dish = intent.getSerializableExtra(DISH) as Dish
        binding.dishTitle.text = dish.name_fr
        binding.dishPrice.text = (dish.prices[0].price + " €")

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
}

package fr.isen.ElGuerzyly.androiderestaurant

import android.content.Intent
import android.os.Bundle
import fr.isen.ElGuerzyly.androiderestaurant.databinding.ActivityHomeBinding



class HomeActivity : MenuActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeButtonStarter.setOnClickListener {
            changeActivity(getString(R.string.entree))
        }

        binding.homeButtonMainDish.setOnClickListener {
            changeActivity(getString(R.string.plat))
        }

        binding.homeButtonDessert.setOnClickListener {
            changeActivity(getString(R.string.dessert))
        }
    }

    private fun changeActivity(category : String) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra(getString(R.string.ExtraCategoryTitle), category)
        }
        startActivity(intent)
    }
}
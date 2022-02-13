package fr.isen.ElGuerzyly.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import fr.isen.ElGuerzyly.androiderestaurant.databinding.ActivityUserBinding


class UserActivity : MenuActivity() {
    private lateinit var binding : ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userLogoutButton.setOnClickListener {
            this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE).edit().remove(getString(R.string.spUserId)).apply()
            Toast.makeText(this, getString(R.string.successLogout), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
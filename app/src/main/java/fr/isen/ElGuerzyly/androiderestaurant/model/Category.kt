package fr.isen.ElGuerzyly.androiderestaurant.model

import java.io.Serializable

data class Category(
    val name_fr : String,
    val items : List<Dish>
) : Serializable

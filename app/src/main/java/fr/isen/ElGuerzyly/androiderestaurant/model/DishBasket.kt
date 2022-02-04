package fr.isen.ElGuerzyly.androiderestaurant.model

import java.io.Serializable

data class DishBasket(
    val dish : Dish,
    var quantity : Int
) : Serializable
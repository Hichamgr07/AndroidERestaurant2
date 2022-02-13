package fr.isen.ElGuerzyly.androiderestaurant.model
import java.io.Serializable

data class BasketList(
    val data : List<DishBasket>
) : Serializable

package fr.isen.ElGuerzyly.androiderestaurant.model
import java.io.Serializable

data class LoginR(
    val data : User,
    val code : Number
) : Serializable
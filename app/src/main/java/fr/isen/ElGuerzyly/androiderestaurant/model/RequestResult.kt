package fr.isen.ElGuerzyly.androiderestaurant.model

import java.io.Serializable

data class RequestResult(
    val data : List<Category>
) : Serializable

package com.example.wordlerick.api

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("characters") val teams: List<Character> = listOf()
)

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String, //te da un link a la imagen, le haces un get y te devuelve la imagen
    val episode: List<String>,
    val url: String,
    val created: String,
){
    val alive: Boolean get() = status == "Alive"
}

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)
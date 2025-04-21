package com.example.wordlerick.apiManager

import retrofit.Call
import retrofit.http.GET
import com.example.wordlerick.api.Character

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<List<Character>>
}
package com.example.wordlerick.apiManager

import com.example.wordlerick.api.CharacterListResponse
import retrofit.Call
import retrofit.http.GET

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterListResponse>
}
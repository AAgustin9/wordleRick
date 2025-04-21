package com.example.wordlerick.apiManager

import android.content.Context
import android.widget.Toast
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject
import retrofit.GsonConverterFactory
import com.example.wordlerick.R
import com.example.wordlerick.api.Character
import com.example.wordlerick.api.CharacterListResponse
import retrofit.Call

class ApiServiceImpl @Inject constructor() {

    fun getCharacters(
        context: Context,
        onSuccess: (List<Character>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.characters_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<CharacterListResponse> = apiService.getCharacters()

        call.enqueue(object : Callback<CharacterListResponse> {
            override fun onResponse(response: Response<CharacterListResponse>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val responseData = response.body()
                    if (responseData != null) {
                        onSuccess(responseData.teams)
                    } else {
                        onFailure(Exception("Bad request"))
                    }
                }
                loadingFinished()
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get characters", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }
}
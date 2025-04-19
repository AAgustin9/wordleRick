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

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<Character>> = service.getCharacters()

        call.enqueue(object : Callback<List<Character>> {
            override fun onResponse(response: Response<List<Character>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null) {
                    if (response.isSuccess) {
                        val characters: List<Character> = response.body()
                        onSuccess(characters)
                    } else {
                        onFailure(Exception("Bad request"))
                    }
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get characters", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }
}
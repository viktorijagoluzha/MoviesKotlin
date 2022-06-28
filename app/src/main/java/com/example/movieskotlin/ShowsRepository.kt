package com.example.movieskotlin



import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ShowsRepository {

    private val api: Api
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getPopularTVShows(
        page: Int = 1,
        onSuccess: (movies: List<TVShow>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularTVShows(page = page)
            .enqueue(object : Callback<GetTVShowResponse> {
                override fun onResponse(
                    call: Call<GetTVShowResponse>,
                    response: Response<GetTVShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.shows)
                            Log.d(responseBody.toString(), "Fetching movies")
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetTVShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }


}



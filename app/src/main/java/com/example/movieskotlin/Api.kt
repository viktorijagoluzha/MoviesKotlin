package com.example.movieskotlin

import com.example.movieskotlin.GetTVShowResponse as GetMoviesResponse
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query

interface Api {
    @GET("trending/tv/week")
    fun getPopularTVShows(
        @Query("api_key") apiKey: String = "f33521953035af3fc3162fe1ac22e60c",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>



}
package com.example.movieskotlin

public final class GetTVShowResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val shows: List<TVShow>,
    @SerializedName("total_pages") val pages: Int
)

annotation class SerializedName(val value: String)

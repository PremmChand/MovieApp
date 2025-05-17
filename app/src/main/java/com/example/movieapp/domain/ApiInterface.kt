package com.example.movieapp.domain

import com.example.movieapp.models.Details
import com.example.movieapp.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
@GET("movies")
suspend fun getMovies(
@Query("page")page:Int
):Response<MovieList>

@GET("movies/{movie_id}")
suspend fun getDetailsById(
    @Path("movie_id")id:Int
):Response<Details>
}
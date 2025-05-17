package com.example.movieapp.viewModal

import com.example.movieapp.models.Details
import com.example.movieapp.models.MovieList
import com.example.movieapp.utils.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getMovieList(page:Int):Response<MovieList>{
        return RetrofitInstance.api.getMovies(page)
    }

    suspend fun getDetailsById(id:Int):Response<Details>{
        return RetrofitInstance.api.getDetailsById(id = id)
    }
}
package com.example.movieapp.viewModal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.models.Data
import com.example.movieapp.models.Details
import com.example.movieapp.paging.PaginationFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModal : ViewModel() {
    private val repository = Repository()
    var state by mutableStateOf(ScreenState())
    var id by mutableIntStateOf(0)

    private  val pagination = PaginationFactory(
        initialPage = state.page,
        onLoadUpdated = {
            state.copy(
                isLoading = it
            )
        },
        onRequest = {nextPage ->
            repository.getMovieList(nextPage)
        },
        getNextKey = {
            state.page +1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = {items, newPage->
            state = state.copy(
                movies = state.movies + items.data,
                page = newPage,
                endReached = state.page == 25
            )
        }
    )

   /* init {
        viewModelScope.launch {

            try {
                val response = repository.getMovieList(state.page)

                if (response.isSuccessful) {
                    response.body()?.let { movieList ->
                        state = state.copy(movies = movieList.data)
                    } ?: run {
                        state = state.copy(movies = emptyList())
                    }
                } else {
                    state = state.copy(movies = emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ErrorMovieViewModel", "Network call failed: ${e.message}")
                state = state.copy(movies = emptyList())
            }
        }
    }*/

    init {
        loadNextItems()
    }

    fun loadNextItems(){
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }


    fun getdetailsById(){
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            try {
                val response = repository.getDetailsById(id=id)
                withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                state = state.copy(
                    detailsData = response.body()!!,
                            isLoading = false
                )
                }
                else{

                    Log.d("Error fetching details:", "${response.errorBody()}")
                }
                }
            }catch (e:Exception){
                state = state.copy(error = e.message, isLoading = false)
            }
        }
    }

    /*fun getdetailsById() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)

            try {
                val response = repository.getDetailsById(id)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { details ->
                            state = state.copy(detailsData = details, isLoading = false)
                        } ?: run {
                            Log.e("MovieViewModal", "Details response was empty")
                            state = state.copy(isLoading = false, error = "No details found")
                        }
                    } else {
                        Log.e("MovieViewModal", "Error fetching details: ${response.errorBody()}")
                        state = state.copy(isLoading = false, error = "Failed to fetch details")
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieViewModal", "Exception fetching details: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    state = state.copy(isLoading = false, error = e.message)
                }
            }
        }
    }*/
}




data class ScreenState(
    val movies: List<Data> = emptyList(), val page: Int = 1,
    val detailsData:Details = Details(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached:Boolean = false,
)
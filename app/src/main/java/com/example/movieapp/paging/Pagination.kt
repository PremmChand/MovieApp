package com.example.movieapp.paging

interface Pagination<Key, Item> {
    suspend fun loadNextPage()

    fun reset()
}
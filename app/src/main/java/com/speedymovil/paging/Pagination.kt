package com.speedymovil.paging

interface Pagination<Key, Item> {
    suspend fun loadNextPage()
    fun reset()
}
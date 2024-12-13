package com.speedymovil.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speedymovil.model.HitsModel
import com.speedymovil.paging.PaginationFactory
import kotlinx.coroutines.launch

class HitsViewModel : ViewModel() {

    private val repository = Repository()
    var state by mutableStateOf(ScreenState())
    var id by mutableIntStateOf(0)

    private val pagination = PaginationFactory(
        initialPage = state.page,
        onLoadUpdated = {
            state = state.copy(
                isLoading = it
            )
        },
        onRequest = {nextPage ->
            repository.getHitsList(nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = {items, newPage ->
            state = state.copy(
                hits = state.hits + items.hits,
                page = newPage,
                endReached = state.page == 20
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }

    fun getDetailsById() {
        try {

        } catch (e: Exception) {

        }
    }

}

data class ScreenState(
    val hits: List<HitsModel> = emptyList(),
    val page: Int = 1,
    val endReached: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)
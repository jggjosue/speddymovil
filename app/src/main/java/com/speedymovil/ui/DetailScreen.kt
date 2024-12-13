package com.speedymovil.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.speedymovil.viewModel.HitsViewModel

@Composable
fun DetailScreen(
    id: Int
) {
    val movieViewModel = viewModel<HitsViewModel>()
    movieViewModel.id = id
    val state = movieViewModel.state
}
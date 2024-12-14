package com.speedymovil.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.speedymovil.viewModel.HitsViewModel

@Composable
fun DetailScreen(
    id: Int
) {
    val movieViewModel = viewModel<HitsViewModel>()
    movieViewModel.id = id
    val state = movieViewModel.state
    //Toast.makeText(LocalContext.current, "id = $id", Toast.LENGTH_SHORT).show()
}
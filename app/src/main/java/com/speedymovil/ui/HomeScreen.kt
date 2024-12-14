package com.speedymovil.ui.theme

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.DismissState
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.DismissValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.speedymovil.viewModel.HitsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController) {
    val movieViewModel = viewModel<HitsViewModel>()
    val state = movieViewModel.state

    SpeedymovilTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    state.hits.size,
                    key = { it }
                ) { index ->
                    if (index >= state.hits.size - 1 && !state.endReached && !state.isLoading) {
                        movieViewModel.loadNextItems()
                    }
                    val list = remember {
                        mutableStateListOf(
                            state.hits[index]
                        )
                    }

                    SwipeToDeleteContainer(
                        item = state.hits[index],
                        onDelete = {
                            list -= state.hits[index]
                        }
                    ) { item ->
                        Column(
                            Modifier
                                .wrapContentSize()
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("detail/${item.objectID}")
                                },
                        ) {
                            Text(
                                text = item.story_title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(16.dp),
                                textAlign = TextAlign.Start,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                            )
                            Row {
                                Text(
                                    text = item.author + " - " + item.created_at,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(
                                            start = 16.dp,
                                            bottom = 8.dp
                                        ),
                                    color = Color.Black,
                                    maxLines = 2
                                )
                            }
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black),
                            )
                        }
                    }
                }

                item(state.isLoading) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                16.dp
                            ),
                        horizontalArrangement = Arrangement.Absolute.Center
                    ) {
                        CircularProgressIndicator(color = ProgressIndicatorDefaults.circularColor)
                    }
                    if (!state.error.isNullOrEmpty()) {
                        Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text("Delete")
    }
}

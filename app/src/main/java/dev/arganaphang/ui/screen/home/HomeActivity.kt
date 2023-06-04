package dev.arganaphang.ui.screen.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dev.arganaphang.todolist.data.Todo
import dev.arganaphang.ui.component.CardTodo
import dev.arganaphang.ui.component.CreateTodo
import dev.arganaphang.ui.theme.TodoListTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val todos = viewModel.todos.collectAsState(initial = emptyList()).value
            HomeScreen(
                todos,
                onUpdate = {
                    viewModel.update(it)
                },
                onSubmit = {
                    viewModel.add(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(todos: List<Todo>, onUpdate: (Long) -> Unit, onSubmit: (String) -> Unit) {
    val courutineScope = rememberCoroutineScope()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    TodoListTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    openBottomSheet = !openBottomSheet
                }) {
                    Icon(Icons.Rounded.Add, contentDescription = "", tint = Color.White)
                }
            }
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(todos.size) {
                    CardTodo(todos[it], onUpdate)
                }
            }

        }
        if (openBottomSheet) {
            CreateTodo(
                bottomSheetState = bottomSheetState,
                onDismiss = {
                    openBottomSheet = false
                },
                onSubmit = { title ->
                    courutineScope.launch {
                        bottomSheetState.hide()
                        onSubmit(title)
                    }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = false
                        }
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeActivityPreview() {
    HomeScreen(arrayListOf(), onUpdate = {}, onSubmit = {})
}
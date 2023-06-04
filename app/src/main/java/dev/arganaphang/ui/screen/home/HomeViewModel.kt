package dev.arganaphang.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arganaphang.repository.TodoRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    val todos = todoRepository.findAll()

    fun add(title: String) {
        if (title.isBlank()) {
            return
        }
        viewModelScope.launch {
           todoRepository.create(title)
        }
    }

    fun update(id: Long) {
        viewModelScope.launch {
           todoRepository.toggleIsDone(id)
        }
    }

}
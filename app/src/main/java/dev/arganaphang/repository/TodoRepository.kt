package dev.arganaphang.repository

import dev.arganaphang.todolist.data.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun create(title: String)
    suspend fun toggleIsDone(id: Long)
    suspend fun findOneByID(id: Long): Todo?
    fun findAll(): Flow<List<Todo>>
}
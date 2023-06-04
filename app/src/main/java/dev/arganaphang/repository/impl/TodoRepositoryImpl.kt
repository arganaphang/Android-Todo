package dev.arganaphang.repository.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.arganaphang.Database
import dev.arganaphang.repository.TodoRepository
import dev.arganaphang.todolist.data.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date

class TodoRepositoryImpl(
    private val database: Database
) : TodoRepository{
    override suspend fun create(title: String) {
        withContext(Dispatchers.IO) {
            val now = Date()
            database.todoQueries.insert(now.time, title, false, now)
        }
    }

    override suspend fun toggleIsDone(id: Long) {
        withContext(Dispatchers.IO) {
            val todo = database.todoQueries.findOneByID(id).executeAsOneOrNull() ?: return@withContext
            database.todoQueries.toggle(!todo.isDone, id)
        }
    }

    override suspend fun findOneByID(id: Long): Todo? {
        return database.todoQueries.findOneByID(id).executeAsOneOrNull()
    }

    override fun findAll(): Flow<List<Todo>> {
        return database.todoQueries.findAll().asFlow().mapToList(Dispatchers.IO)
    }
}


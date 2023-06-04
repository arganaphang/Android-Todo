package dev.arganaphang.di


import android.app.Application
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arganaphang.Database
import dev.arganaphang.repository.TodoRepository
import dev.arganaphang.repository.impl.TodoRepositoryImpl
import dev.arganaphang.todolist.data.Todo
import java.util.Date
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoRepository(app: Application): TodoRepository {
        val driver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = app,
            name = "person.db"
        )
        val createdAtAdapter = object : ColumnAdapter<Date, Long> {
            override fun decode(databaseValue: Long): Date {
                return Date(databaseValue)
            }

            override fun encode(value: Date): Long {
                return value.time
            }
        }
        return TodoRepositoryImpl(Database.invoke(driver = driver, todoAdapter = Todo.Adapter(
            createdAtAdapter = createdAtAdapter
        )))
    }
}

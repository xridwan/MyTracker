package id.eve.mytracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.data.entity.TaskWithProject
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task WHERE projectId = :projectId")
    fun getTasksByProjectFlow(projectId: Long): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :taskId LIMIT 1")
    fun getTaskWithProjectFlow(taskId: Long): Flow<TaskWithProject?>

    @Query("SELECT * FROM task WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Long): Task?
}
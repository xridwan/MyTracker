package id.eve.mytracker.domain.repository

import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.data.entity.TaskWithProject
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksByProjectFlow(projectId: Long): Flow<List<Task>>
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getTaskWithProjectFlow(taskId: Long): Flow<TaskWithProject?>
    suspend fun getTaskById(taskId: Long): Task?

}
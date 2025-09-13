package id.eve.mytracker.data.repository

import id.eve.mytracker.data.db.dao.TaskDao
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.data.entity.TaskWithProject
import id.eve.mytracker.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasksByProjectFlow(projectId: Long): Flow<List<Task>> {
        return taskDao.getTasksByProjectFlow(projectId)
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insert(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    override fun getTaskWithProjectFlow(taskId: Long): Flow<TaskWithProject?> {
        return taskDao.getTaskWithProjectFlow(taskId)
    }

    override suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTaskById(taskId)
    }
}
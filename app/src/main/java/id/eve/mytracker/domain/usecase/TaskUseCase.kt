package id.eve.mytracker.domain.usecase

import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.data.entity.TaskWithProject
import id.eve.mytracker.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskByIdUseCase(
    private val repo: TaskRepository
) {
    suspend operator fun invoke(taskId: Long): Task? = repo.getTaskById(taskId)
}

class GetAllTasksByProjectFlow(
    private val repo: TaskRepository
) {
    operator fun invoke(projectId: Long): Flow<List<Task>> =
        repo.getAllTasksByProjectFlow(projectId)
}

class InsertTaskUseCase(
    private val repo: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repo.insertTask(task)
}

class UpdateTaskUseCase(
    private val repo: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repo.updateTask(task)
}

class DeleteTaskUseCase(
    private val repo: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repo.deleteTask(task)
}
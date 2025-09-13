package id.eve.mytracker.present.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.domain.usecase.DeleteTaskUseCase
import id.eve.mytracker.domain.usecase.GetAllTasksByProjectFlow
import id.eve.mytracker.domain.usecase.GetTaskByIdUseCase
import id.eve.mytracker.domain.usecase.InsertTaskUseCase
import id.eve.mytracker.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TaskUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val task: Task? = null,
    val error: String? = null
)

class TaskViewModel(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

    fun getTaskById(taskId: Long) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId)
            _uiState.value = _uiState.value.copy(task = task)
        }
    }

    fun addTask(projectId: Long, name: String, bobot: Int) {
        viewModelScope.launch {
            val newTask = Task(
                name = name,
                status = "draft",
                projectId = projectId,
                bobot = bobot
            )
            insertTaskUseCase(newTask)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}
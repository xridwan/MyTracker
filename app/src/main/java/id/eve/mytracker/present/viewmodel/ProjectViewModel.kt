package id.eve.mytracker.present.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import id.eve.mytracker.domain.usecase.DeleteProjectUseCase
import id.eve.mytracker.domain.usecase.GetAllProjectsUseCase
import id.eve.mytracker.domain.usecase.GetAllProjectsWithTasksFlow
import id.eve.mytracker.domain.usecase.GetProjectByIdUseCase
import id.eve.mytracker.domain.usecase.InsertProjectUseCase
import id.eve.mytracker.domain.usecase.UpdateProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProjectUiState(
    val isLoading: Boolean = false,
    val project: Project? = null,
    val projects: List<Project> = emptyList(),
    val projectsWithTasks: List<ProjectWithTasks> = emptyList(),
    val error: String? = null
)

class ProjectViewModel(
    private val getAllProjectsUseCase: GetAllProjectsUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getAllProjectsWithTasksFlow: GetAllProjectsWithTasksFlow,
    private val getProjectByIdUseCase: GetProjectByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectUiState())
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    fun loadProjectsWithTasks() {
        viewModelScope.launch {
            getAllProjectsWithTasksFlow()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
                .collect { projectsWithTasks ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            projectsWithTasks = projectsWithTasks,
                            error = null
                        )
                    }
                    println("ProjectViewModel: $projectsWithTasks")
                }
        }
    }

    fun getProjectById(projectId: Long) {
        viewModelScope.launch {
            val project = getProjectByIdUseCase(projectId)
            _uiState.value = _uiState.value.copy(project = project)
            println("ProjectViewModel: ${project?.completionProgress}")
        }
    }

    fun addProject(name: String) {
        viewModelScope.launch {
            val newProject = Project(
                name = name,
                status = "draft",
                completionProgress = 0
            )
            insertProjectUseCase(newProject)
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            updateProjectUseCase(project)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            deleteProjectUseCase(project)
        }
    }
}
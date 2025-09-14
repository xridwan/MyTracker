package id.eve.mytracker.domain.usecase

import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import id.eve.mytracker.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class GetProjectByIdUseCase(
    private val repo: ProjectRepository
) {
    suspend operator fun invoke(projectId: Long): Project? = repo.getProjectById(projectId)
}

class GetAllProjectsWithTasksFlow(
    private val repo: ProjectRepository
) {
    operator fun invoke(): Flow<List<ProjectWithTasks>> = repo.getAllProjectsWithTasksFlow()
}

class GetProjectWithTasksFlow(
    private val repo: ProjectRepository
) {
    operator fun invoke(projectId: Long): Flow<ProjectWithTasks> =
        repo.getProjectWithTasks(projectId)
}

class GetAllProjectsUseCase(
    private val repo: ProjectRepository
) {
    operator fun invoke(): Flow<List<Project>> = repo.getAllProjects()
}

class InsertProjectUseCase(
    private val repo: ProjectRepository
) {
    suspend operator fun invoke(project: Project) = repo.insertProject(project)
}

class UpdateProjectUseCase(
    private val repo: ProjectRepository
) {
    suspend operator fun invoke(project: Project) = repo.updateProject(project)
}

class DeleteProjectUseCase(
    private val repo: ProjectRepository
) {
    suspend operator fun invoke(project: Project) = repo.deleteProject(project)
}
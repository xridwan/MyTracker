package id.eve.mytracker.domain.repository

import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getAllProjects(): Flow<List<Project>>
    suspend fun insertProject(project: Project)
    suspend fun updateProject(project: Project)
    suspend fun deleteProject(project: Project)
    fun getAllProjectsWithTasksFlow(): Flow<List<ProjectWithTasks>>
    fun getProjectWithTasks(projectId: Long): Flow<ProjectWithTasks>
    suspend fun getProjectById(projectId: Long): Project?
}
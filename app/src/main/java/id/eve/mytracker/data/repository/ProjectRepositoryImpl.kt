package id.eve.mytracker.data.repository

import id.eve.mytracker.core.helper.updateProjectStatus
import id.eve.mytracker.data.db.dao.ProjectDao
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import id.eve.mytracker.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepositoryImpl(
    private val projectDao: ProjectDao
) : ProjectRepository {

    override fun getAllProjects(): Flow<List<Project>> {
        return projectDao.getAllProjectsFlow()
    }

    override suspend fun insertProject(project: Project) {
        projectDao.insert(project)
    }

    override suspend fun updateProject(project: Project) {
        projectDao.update(project)
    }

    override suspend fun deleteProject(project: Project) {
        projectDao.delete(project)
    }

    override fun getAllProjectsWithTasksFlow(): Flow<List<ProjectWithTasks>> {
        return projectDao.getAllProjectsWithTasksFlow().map { projectsWithTasks ->
            projectsWithTasks.map { it.updateProjectStatus() }
        }
    }

    override suspend fun getProjectById(projectId: Long): Project? {
        return projectDao.getProjectById(projectId)
    }
}
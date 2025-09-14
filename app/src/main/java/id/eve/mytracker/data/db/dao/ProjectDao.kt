package id.eve.mytracker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import id.eve.mytracker.data.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: Project): Long

    @Update
    suspend fun update(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Query("SELECT * FROM project")
    fun getAllProjectsFlow(): Flow<List<Project>>

    @Transaction
    @Query("SELECT * FROM project")
    fun getAllProjectsWithTasksFlow(): Flow<List<ProjectWithTasks>>

    @Transaction
    @Query("SELECT * FROM project WHERE id = :projectId LIMIT 1")
    fun getProjectWithTasksFlow(projectId: Long): Flow<ProjectWithTasks?>

    @Query("SELECT * FROM project WHERE id = :projectId LIMIT 1")
    suspend fun getProjectById(projectId: Long): Project?
}
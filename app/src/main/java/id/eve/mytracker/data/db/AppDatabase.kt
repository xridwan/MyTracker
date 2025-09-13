package id.eve.mytracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.eve.mytracker.data.db.dao.ProjectDao
import id.eve.mytracker.data.db.dao.TaskDao
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.Task

@Database(
    entities = [
        Project::class,
        Task::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao

}
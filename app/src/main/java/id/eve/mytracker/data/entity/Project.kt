package id.eve.mytracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val status: String, // "draft", "in_progress", "done"
    val completionProgress: Int // 0 - 100
)

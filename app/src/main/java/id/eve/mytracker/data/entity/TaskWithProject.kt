package id.eve.mytracker.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithProject(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "projectId", // column in Task
        entityColumn = "id"         // column in Project
    )
    val project: Project
)
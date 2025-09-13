package id.eve.mytracker.core.helper

import id.eve.mytracker.data.entity.ProjectWithTasks

fun ProjectWithTasks.updateProjectStatus(): ProjectWithTasks {
    val newStatus = when {
        tasks.isEmpty() -> project.status
        tasks.all { it.status == "done" } -> "done"
        tasks.all { it.status == "draft" } -> "draft"
        else -> "in_progress"
    }

    val completion = if (tasks.isNotEmpty()) {
        (tasks.count { it.status == "done" } * 100) / tasks.size
    } else {
        0
    }

    return this.copy(
        project = project.copy(
            status = newStatus,
            completionProgress = completion
        )
    )
}
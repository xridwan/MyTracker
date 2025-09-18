package id.eve.mytracker.present.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.eve.mytracker.core.helper.status
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.ProjectWithTasks
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.databinding.ViewItemProjectBinding

class ProjectAdapter(
    private val onAddTaskClickListener: OnAddTaskClickListener
) : ListAdapter<ProjectWithTasks, ProjectAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    inner class ProjectViewHolder(private val binding: ViewItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root), TaskAdapter.OnItemClickListener {

        private val taskAdapter = TaskAdapter(this)

        init {
            binding.rvTasks.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = taskAdapter
            }
        }

        fun bind(projectWithTasks: ProjectWithTasks) {
            val project = projectWithTasks.project

            binding.tvName.text = project.name
            binding.tvStatus.text = project.status.status()

            taskAdapter.submitList(projectWithTasks.tasks)

            binding.tvName.setOnClickListener {
                onAddTaskClickListener.onUpdateProjectClick(project)
            }

            binding.ivAddTask.setOnClickListener {
                onAddTaskClickListener.onAddTaskClick(project)
            }
        }

        override fun onItemClick(task: Task) {
            onAddTaskClickListener.onTaskClick(task, getItem(adapterPosition).project)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ViewItemProjectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnAddTaskClickListener {
        fun onUpdateProjectClick(project: Project)
        fun onAddTaskClick(project: Project)
        fun onTaskClick(task: Task, project: Project)
    }
}

class ProjectDiffCallback : DiffUtil.ItemCallback<ProjectWithTasks>() {
    override fun areItemsTheSame(
        oldItem: ProjectWithTasks,
        newItem: ProjectWithTasks
    ): Boolean = oldItem.project.id == newItem.project.id

    override fun areContentsTheSame(
        oldItem: ProjectWithTasks,
        newItem: ProjectWithTasks
    ): Boolean = oldItem == newItem
}

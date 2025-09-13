package id.eve.mytracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.databinding.ActivityMainBinding
import id.eve.mytracker.present.adapter.ProjectAdapter
import id.eve.mytracker.present.dialog.AddDialogSheet
import id.eve.mytracker.present.dialog.AddProjectDialogSheet
import id.eve.mytracker.present.dialog.UpdateDialogSheet
import id.eve.mytracker.present.dialog.UpdateProjectDialogSheet
import id.eve.mytracker.present.viewmodel.ProjectViewModel
import id.eve.mytracker.present.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ProjectAdapter.OnAddTaskClickListener {

    private lateinit var binding: ActivityMainBinding
    private val projectViewmodel: ProjectViewModel by viewModel()

    private val projectAdapter = ProjectAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvProjects.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = projectAdapter
        }

        setOnClick()
        getAllProjectWithTask()
    }

    private fun setOnClick() {
        binding.fabAddProject.setOnClickListener {
            AddProjectDialogSheet().show(supportFragmentManager, "AddProjectDialogSheet")
        }
    }

    private fun getAllProjectWithTask() {
        projectViewmodel.loadProjectsWithTasks()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectViewmodel.uiState.collect { state ->
                    projectAdapter.submitList(state.projectsWithTasks)
                }
            }
        }
    }

    override fun onUpdateProjectClick(project: Project) {
        UpdateProjectDialogSheet(
            project = project
        ).show(supportFragmentManager, "AddProjectDialogSheet")
    }

    override fun onAddTaskClick(project: Project) {
        AddDialogSheet(
            project = project
        ).show(supportFragmentManager, "AddDialogSheet")
    }

    override fun onTaskClick(
        task: Task,
        project: Project
    ) {
        UpdateDialogSheet(
            task = task,
            project = project
        ).show(supportFragmentManager, "AddDialogSheet")
    }
}
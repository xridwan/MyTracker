package id.eve.mytracker.present.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.eve.mytracker.core.helper.show
import id.eve.mytracker.core.helper.showToast
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.databinding.SheetProjectDialogBinding
import id.eve.mytracker.present.viewmodel.ProjectViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProjectDialogSheet(
    private val project: Project
) : BottomSheetDialogFragment() {

    private val viewModel: ProjectViewModel by viewModel()
    private var _binding: SheetProjectDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetProjectDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        getProjectById()
        setOnClick()
    }

    private fun setView() {
        binding.apply {
            tvTitleProject.text = "Update Project"
            etStatus.show()
            etProgress.show()
            btnAdd.text = "Update"
            btnDelete.show()
        }
    }

    private fun getProjectById() {
        viewModel.getProjectWithTasks(project.id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.apply {
                        etName.setText(state.projectsWithTask?.project?.name)
                        etStatus.setText(state.projectsWithTask?.project?.status)
                        etProgress.setText(state.projectsWithTask?.project?.completionProgress.toString())
                    }
                }
            }
        }
    }

    private fun setOnClick() {
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()

            if (name.isNotEmpty()) {
                val updateProject = project.copy(name = name)
                viewModel.updateProject(updateProject)
                dismiss()
            } else {
                context?.showToast("Please fill all fields")
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteProject(project)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
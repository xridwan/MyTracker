package id.eve.mytracker.present.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.eve.mytracker.core.helper.show
import id.eve.mytracker.core.helper.showToast
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.data.entity.Task
import id.eve.mytracker.databinding.SheetAddDialogBinding
import id.eve.mytracker.present.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateDialogSheet(
    private val task: Task,
    private val project: Project
) : BottomSheetDialogFragment() {

    private val taskViewModel: TaskViewModel by viewModel()
    private var _binding: SheetAddDialogBinding? = null
    private val binding get() = _binding!!

    val statusList = listOf("draft", "in_progress", "done")
    var selectedStatus: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        getTaskById()
        setOnClick()
    }

    private fun setView() {
        binding.apply {
            tvTitleTask.text = "Update Task"
            btnAdd.text = "Update"
            spinnerStatus.show()
            btnDelete.show()
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statusList
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStatus.adapter = adapter

        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = statusList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setOnClick() {
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val bobot = binding.etBobot.text.toString().toIntOrNull()

            if (name.isNotEmpty() && selectedStatus.isNotEmpty() && bobot != null) {
                val updatedTask = task.copy(name = name, status = selectedStatus, bobot = bobot)
                taskViewModel.updateTask(updatedTask)
                dismiss()
            } else {
                context?.showToast("Please fill all fields")
            }
        }

        binding.btnDelete.setOnClickListener {
            taskViewModel.deleteTask(task)
            dismiss()
        }
    }

    private fun getTaskById() {
        taskViewModel.getTaskById(task.id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskViewModel.uiState.collect { state ->
                    binding.apply {
                        etProject.setText(project.name)
                        etName.setText(state.task?.name)

                        selectedStatus = state.task?.status.toString()
                        val position = statusList.indexOf(selectedStatus)
                        if (position >= 0) {
                            binding.spinnerStatus.setSelection(position)
                        }

                        etBobot.setText(state.task?.bobot.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
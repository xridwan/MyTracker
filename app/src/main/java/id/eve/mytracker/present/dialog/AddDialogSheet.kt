package id.eve.mytracker.present.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.eve.mytracker.core.helper.hide
import id.eve.mytracker.core.helper.showToast
import id.eve.mytracker.data.entity.Project
import id.eve.mytracker.databinding.SheetAddDialogBinding
import id.eve.mytracker.present.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDialogSheet(
    private val project: Project
) : BottomSheetDialogFragment() {

    private val taskViewModel: TaskViewModel by viewModel()
    private var _binding: SheetAddDialogBinding? = null
    private val binding get() = _binding!!

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
        binding.apply {
            etProject.setText(project.name)
            btnDelete.hide()

            btnAdd.setOnClickListener {
                val name = etName.text.toString()
                val bobot = etBobot.text.toString().toIntOrNull()

                if (name.isNotEmpty() && bobot != null) {
                    taskViewModel.addTask(project.id, name, bobot)
                    dismiss()
                } else {
                    context?.showToast("Please fill all fields")
                }
            }
        }
    }
}
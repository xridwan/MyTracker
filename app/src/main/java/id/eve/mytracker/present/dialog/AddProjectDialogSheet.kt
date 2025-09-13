package id.eve.mytracker.present.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.eve.mytracker.core.helper.hide
import id.eve.mytracker.core.helper.showToast
import id.eve.mytracker.databinding.SheetProjectDialogBinding
import id.eve.mytracker.present.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProjectDialogSheet : BottomSheetDialogFragment() {

    private val projectViewModel: ProjectViewModel by viewModel()
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
        binding.apply {
            btnDelete.hide()

            btnAdd.setOnClickListener {
                val name = etName.text.toString()

                if (name.isNotEmpty()) {
                    projectViewModel.addProject(name)
                    dismiss()
                } else {
                    context?.showToast("Please fill all fields")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
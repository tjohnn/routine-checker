package com.tjohnn.routinechecks.ui.addeditroutine

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentAddEditRoutineBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.data.model.RoutineDuration
import com.tjohnn.routinechecks.service.AlarmSchedulerService
import com.tjohnn.routinechecks.service.BackgroundAlarmSchedulerService
import com.tjohnn.routinechecks.ui.EmptyData
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.LoadError
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditRoutineFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: FragmentAddEditRoutineBinding
    private var routineId: Int = 0
    private lateinit var viewModel: AddEditRoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[AddEditRoutineViewModel::class.java]

        arguments?.run {
            routineId = getInt(ARG_ROUTINE_ID, 0)
        }
        savedInstanceState?.run {
            routineId = getInt(ARG_ROUTINE_ID, 0)
        }

        if (routineId != 0) {
            viewModel.getRoutineDetails(routineId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditRoutineBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // attach toolbar
        val title = if(routineId == 0) R.string.new_routine else  R.string.edit_routine
        attachToolbar(view, title, true)

        observeViewModel(savedInstanceState)
        if(savedInstanceState != null) {
            savedInstanceState.getParcelable<Routine>(ARG_ROUTINE_FORM)?.let { bindFormData(it) }
        }

        binding.saveButton.setOnClickListener {
            viewModel.saveRoutine(collectFormData())
        }
    }

    private fun observeViewModel(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.frequencyDurations.collect {
                binding.frequencyDurationField.adapter =
                        ArrayAdapter(context!!, android.R.layout.simple_list_item_1, it)
            }
        }
        lifecycleScope.launch {
            if(savedInstanceState == null) {
                viewModel.routine.filterNotNull().collect {
                    bindFormData(it)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.formError.collect {
                binding.errorText.text = it
            }
        }
        lifecycleScope.launch {
            viewModel.onRoutineSaved.collect {
                if (it.contentIfNotUsed  == true) {
                    // reschedule next reminder in case the newly created routine reminder time is earlier than current
                    BackgroundAlarmSchedulerService.startSelf(context!!)
                    activity?.onBackPressed()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.screenState.collect {
                when(it) {
                    is LoadError -> {

                    }
                    is EmptyData -> {

                    }
                    Idle -> {}
                }
            }
        }

    }

    private fun bindFormData(it: Routine) {
        binding.titleField.setText(it.title)
        binding.descriptionField.setText(it.description)
        binding.frequencyField.setText("${it.frequency}")
        var position = viewModel.frequencyDurations.value.indexOf(it.duration.durationValue)
        position = if(position == -1) 0 else position
        binding.frequencyDurationField.setSelection(position)
    }

    private fun collectFormData() = Routine (
            id = routineId,
            title = binding.titleField.text.toString(),
            description = binding.descriptionField.text.toString(),
            frequency = binding.frequencyField.text.toString().toIntOrNull() ?: 0,
            duration = RoutineDuration.fromValue(binding.frequencyDurationField.selectedItem.toString())
    )

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_ROUTINE_ID, routineId)
        outState.putParcelable(ARG_ROUTINE_FORM, collectFormData())
    }


    companion object {
        const val TAG = "AddEditRoutineFragment"
        private const val ARG_ROUTINE_ID = "routineId"
        private const val ARG_ROUTINE_FORM = "form"
        fun newInstance(routineId: Int = 0): AddEditRoutineFragment {
            val args = Bundle()
            args.putInt(ARG_ROUTINE_ID, routineId)
            val fragment = AddEditRoutineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
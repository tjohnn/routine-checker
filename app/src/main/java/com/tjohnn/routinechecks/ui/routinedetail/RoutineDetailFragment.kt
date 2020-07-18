package com.tjohnn.routinechecks.ui.routinedetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.data.model.Routine
import com.tjohnn.routinechecks.databinding.FragmentRoutineDetailBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import com.tjohnn.routinechecks.extensions.hide
import com.tjohnn.routinechecks.extensions.setDrawableStart
import com.tjohnn.routinechecks.extensions.show
import com.tjohnn.routinechecks.service.BackgroundAlarmSchedulerService
import com.tjohnn.routinechecks.ui.EmptyData
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.utils.toDurationText
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RoutineDetailFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: FragmentRoutineDetailBinding
    private var routineId: Int = 0
    private lateinit var viewModel: RoutineDetailViewModel
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[RoutineDetailViewModel::class.java]

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
        binding = FragmentRoutineDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToolbar(view, R.string.routine_detail, true)
        observeViewModel()
        binding.doneButton.setOnClickListener {
            viewModel.markAsDone()
        }

        binding.editButton.setOnClickListener {
            listener.onEditRoutine(routineId)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.routine.filterNotNull().collect {
                Timber.d("Routine: $it")
                bindRoutineData(it)
            }
        }
        lifecycleScope.launch {
            viewModel.snackbarMessage.filterNotNull().collect {
                if(it.contentIfNotUsed != null) {
                    Snackbar.make(view!!, it.peekContent(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.screenState.collect {
                when(it) {
                    is LoadError -> {
                        Timber.d("routines error: ${it.message}")
                    }
                    is EmptyData -> {
                        Timber.d("Empty data")
                    }
                    Idle -> {}
                }
            }
        }

    }

    private fun bindRoutineData(routine: Routine) {
        with(binding) {
            routineTitleText.text = routine.title
            descriptionText.text = routine.description
            nextCheckTimeText.text = if(routine.isPending)
                routine.previousCheckTime.toDurationText(context!!)
            else
                routine.nextCheckTime.toDurationText(context!!)

            val total = routine.numberOfDone + routine.numberOfMissed
            if(total != 0) {
                val grade = routine.numberOfDone / total * 100.0
                if(grade < 70.0) {
                    statusText.setDrawableStart(R.drawable.ic_outline_mood_bad_24)
                    statusText.setText(R.string.bad_job)
                } else {
                    statusText.setDrawableStart(R.drawable.ic_baseline_thumb_up_24)
                    statusText.setText(R.string.good_job)
                }
            }

            if(routine.isPending) {
                doneButton.show()
            } else {
                doneButton.hide()
            }
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    interface OnFragmentInteractionListener {
        fun onEditRoutine(id: Int)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_ROUTINE_ID, routineId)
    }


    companion object {
        const val TAG = "AddEditRoutineFragment"
        private const val ARG_ROUTINE_ID = "routineId"
        fun newInstance(routineId: Int = 0): RoutineDetailFragment {
            val args = Bundle()
            args.putInt(ARG_ROUTINE_ID, routineId)
            val fragment = RoutineDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
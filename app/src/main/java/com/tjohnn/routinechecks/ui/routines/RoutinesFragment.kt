package com.tjohnn.routinechecks.ui.routines

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentRoutinesBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import com.tjohnn.routinechecks.ui.EmptyData
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.widget.MiddleDividerItemDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RoutinesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: RoutinesViewModel
    private lateinit var binding: FragmentRoutinesBinding
    private lateinit var routinesAdapter: RoutineAdapter
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[RoutinesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoutinesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToolbar(view, R.string.routines, false)
        setupAdapter()
        observeScreenState()
    }

    private fun setupAdapter() {
        routinesAdapter = RoutineAdapter {
            listener.onOpenRoutineDetail(it.id)
        }
        with(binding.routineList) {
            layoutManager = LinearLayoutManager(context)
            val decoration =
                MiddleDividerItemDecoration(context, MiddleDividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = routinesAdapter
        }
    }

    private fun observeScreenState() {
        lifecycleScope.launch {
            viewModel.screenState.collect {
                when (it) {
                    is LoadError -> {
                        Timber.d("routines error: ${it.message}")
                    }
                    is EmptyData -> {
                        Timber.d("routines empty")
                    }
                    Idle -> {
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.routines.collect {
                Timber.d("routines: $it")
                routinesAdapter.items = it
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnFragmentInteractionListener) {
            listener = parentFragment as OnFragmentInteractionListener
        }
    }

    interface OnFragmentInteractionListener {
        fun onOpenRoutineDetail(id: Int)
    }

    companion object {
        fun newInstance(): RoutinesFragment {
            val args = Bundle()

            val fragment = RoutinesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
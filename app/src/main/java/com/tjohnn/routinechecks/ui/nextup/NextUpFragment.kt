package com.tjohnn.routinechecks.ui.nextup

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentNextUpBinding
import com.tjohnn.routinechecks.databinding.FragmentRoutinesBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import com.tjohnn.routinechecks.ui.EmptyData
import com.tjohnn.routinechecks.ui.Idle
import com.tjohnn.routinechecks.ui.LoadError
import com.tjohnn.routinechecks.ui.routines.RoutineAdapter
import com.tjohnn.routinechecks.ui.routines.RoutinesFragment
import com.tjohnn.routinechecks.ui.routines.RoutinesViewModel
import com.tjohnn.routinechecks.widget.MiddleDividerItemDecoration
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import android.view.LayoutInflater as LayoutInflater1

class NextUpFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentNextUpBinding
    private lateinit var listener: OnFragmentInteractionListener
    private lateinit var viewModel: NextUpViewModel
    private lateinit var routinesAdapter: RoutineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[NextUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNextUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToolbar(view, R.string.next_up, false)
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
                routinesAdapter.items = it
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is OnFragmentInteractionListener) {
            listener = parentFragment as OnFragmentInteractionListener
        }
    }

    interface OnFragmentInteractionListener {
        fun onOpenRoutineDetail(id: Int)
    }


    companion object {
        fun newInstance(): NextUpFragment {
            val args = Bundle()

            val fragment = NextUpFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
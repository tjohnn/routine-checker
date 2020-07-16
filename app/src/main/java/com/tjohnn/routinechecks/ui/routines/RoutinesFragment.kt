package com.tjohnn.routinechecks.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentRoutinesBinding
import com.tjohnn.routinechecks.extensions.attachToolbar

class RoutinesFragment : BaseFragment() {

    lateinit var binding: FragmentRoutinesBinding

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
        // attach toolbar
        attachToolbar(view, R.string.routines, false)
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
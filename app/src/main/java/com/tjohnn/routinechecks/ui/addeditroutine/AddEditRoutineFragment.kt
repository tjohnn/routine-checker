package com.tjohnn.routinechecks.ui.addeditroutine

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentAddEditRoutineBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import android.view.LayoutInflater as LayoutInflater1

class AddEditRoutineFragment : BaseFragment() {

    lateinit var binding: FragmentAddEditRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditRoutineBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // attach toolbar
        attachToolbar(view, R.string.new_routine, true)
    }


    companion object {
        const val TAG = "AddEditRoutineFragment"
        fun newInstance(): AddEditRoutineFragment {
            val args = Bundle()

            val fragment = AddEditRoutineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
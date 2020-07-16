package com.tjohnn.routinechecks.ui.nextup

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentNextUpBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import android.view.LayoutInflater as LayoutInflater1

class NextUpFragment : BaseFragment() {

    lateinit var binding: FragmentNextUpBinding

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
        // attach toolbar
        attachToolbar(view, R.string.next_up, false)
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
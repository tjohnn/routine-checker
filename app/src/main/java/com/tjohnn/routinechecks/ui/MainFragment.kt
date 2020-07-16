package com.tjohnn.routinechecks.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseFragment
import com.tjohnn.routinechecks.databinding.FragmentMainBinding
import com.tjohnn.routinechecks.databinding.FragmentNextUpBinding
import com.tjohnn.routinechecks.extensions.attachToolbar
import com.tjohnn.routinechecks.ui.nextup.NextUpFragment
import com.tjohnn.routinechecks.ui.routines.RoutinesFragment
import android.view.LayoutInflater as LayoutInflater1

class MainFragment : BaseFragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var listener: OnFragmentInteractionListener

    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null) {
            setUpPager()
        }
        binding.newRoutineFab.setOnClickListener {
            listener.onAddNewRoutine()
        }
    }

    private fun setUpPager() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_next_up -> binding.viewPager.setCurrentItem(0, false)
                R.id.nav_all -> binding.viewPager.setCurrentItem(1, false)
            }
            true
        }
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.adapter = MainPagerAdapter(childFragmentManager)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    companion object {
        const val TAG = "MainFragment"
        fun newInstance(): MainFragment {
            val args = Bundle()

            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnFragmentInteractionListener {
        fun onAddNewRoutine()
    }


    inner class MainPagerAdapter(
        manager: FragmentManager
    ) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return if(position == 0) {
                NextUpFragment.newInstance()
            } else {
                RoutinesFragment.newInstance()
            }
        }

        override fun getCount() = 2

    }
}
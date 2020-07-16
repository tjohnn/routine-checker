package com.tjohnn.routinechecks.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseActivity
import com.tjohnn.routinechecks.databinding.ActivityMainBinding
import com.tjohnn.routinechecks.extensions.addFragmentToBackStack
import com.tjohnn.routinechecks.extensions.replaceFragment
import com.tjohnn.routinechecks.ui.addeditroutine.AddEditRoutineFragment
import com.tjohnn.routinechecks.ui.nextup.NextUpFragment
import com.tjohnn.routinechecks.ui.routines.RoutinesFragment

class MainActivity : BaseActivity(), MainFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), MainFragment.TAG, R.id.screen_content)
        }
    }

    override fun onAddNewRoutine() {
        addFragmentToBackStack(AddEditRoutineFragment.newInstance(), AddEditRoutineFragment.TAG, R.id.screen_content)
    }
}
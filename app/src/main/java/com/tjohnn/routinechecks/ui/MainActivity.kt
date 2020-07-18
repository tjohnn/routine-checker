package com.tjohnn.routinechecks.ui

import android.os.Bundle
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.base.BaseActivity
import com.tjohnn.routinechecks.databinding.ActivityMainBinding
import com.tjohnn.routinechecks.extensions.addFragmentToBackStack
import com.tjohnn.routinechecks.extensions.replaceFragment
import com.tjohnn.routinechecks.service.AlarmSchedulerService
import com.tjohnn.routinechecks.service.BackgroundAlarmSchedulerService
import com.tjohnn.routinechecks.ui.addeditroutine.AddEditRoutineFragment
import com.tjohnn.routinechecks.ui.routinedetail.RoutineDetailFragment

class MainActivity : BaseActivity(),
    MainFragment.OnFragmentInteractionListener,
    RoutineDetailFragment.OnFragmentInteractionListener
{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance(), MainFragment.TAG, R.id.screen_content)
        }

        BackgroundAlarmSchedulerService.startSelf(this)
    }

    override fun onAddNewRoutine() {
        addFragmentToBackStack(AddEditRoutineFragment.newInstance(), AddEditRoutineFragment.TAG, R.id.screen_content)
    }

    override fun onEditRoutine(id: Int) {
        addFragmentToBackStack(AddEditRoutineFragment.newInstance(id), AddEditRoutineFragment.TAG, R.id.screen_content)
    }

    override fun onOpenRoutineDetail(id: Int) {
        addFragmentToBackStack(RoutineDetailFragment.newInstance(id), RoutineDetailFragment.TAG, R.id.screen_content)
    }
}